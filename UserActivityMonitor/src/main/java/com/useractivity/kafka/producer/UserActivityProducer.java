package com.useractivity.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by SatishDivakarla on 4/21/15.
 */
public class UserActivityProducer {

    private static UserActivityProducer instance = null;

    public static UserActivityProducer getInstance() {
        if (instance == null) {
            instance = new UserActivityProducer();
        }
        return instance;
    }


    public void produce(String coordinateX, String coordinateY) {
        System.out.println("coordinateX = " + coordinateX);
        System.out.println("coordinateY = " + coordinateY);
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        String msg = System.currentTimeMillis() + "," + coordinateX + "," + coordinateY;
        KeyedMessage<String, String> data = new KeyedMessage<String, String>("useractivity", msg);
        producer.send(data);
        producer.close();
    }
}
