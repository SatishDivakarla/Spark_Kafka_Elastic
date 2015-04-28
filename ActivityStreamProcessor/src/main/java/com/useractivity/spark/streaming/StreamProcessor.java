package com.useractivity.spark.streaming;

import com.elasticsearch.practice.util.ElasticSearchUtil;
import com.useractivity.model.Quadrant;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.codehaus.jackson.map.ObjectMapper;
import scala.Tuple2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SatishDivakarla on 4/21/15.
 */
public class StreamProcessor implements Serializable {

    private static com.useractivity.spark.streaming.StreamProcessor instance = null;

    public static com.useractivity.spark.streaming.StreamProcessor getInstance(){
        if(instance == null){
            instance = new com.useractivity.spark.streaming.StreamProcessor();
        }
        return instance;
    }


    public void processStream(String topicName){
        SparkConf conf = new SparkConf().setAppName("UserActivityStreaming");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(conf, Durations.seconds(5));
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put(topicName, 2);
        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(javaStreamingContext, "localhost", "group1", topicMap);

        JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
            @Override
            public String call(Tuple2<String, String> tuple2) {
                return tuple2._2();
            }
        });

        JavaDStream<Quadrant> quadrants = lines.map(new QuadrantCreator.LinesToQuadrant());

        quadrants.foreachRDD(new Function<JavaRDD<Quadrant>, Void>() {
            @Override
            public Void call(JavaRDD<Quadrant> quadrantJavaRDD) throws Exception {
                List<Quadrant> quadrantList = quadrantJavaRDD.collect();
                ElasticSearchUtil util = new ElasticSearchUtil();
                util.getClient();
                ObjectMapper objectMapper = new ObjectMapper();
                for (Quadrant quadrant: quadrantList){
                    util.indexRecord(objectMapper.writeValueAsString(quadrant), "useractivities", "quadrant");
                }
                util.closeConnection();
                return null;
            }
        });

        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }

    public static void main(String[] args) {
       getInstance().processStream("useractivity");
    }
}

