package com.useractivity.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * This class writes the coordinates and time stamp on which the activity happened
 * to Kafka topic called "useractivity"
 * Created by SatishDivakarla on 4/21/15.
 */
public class UserActivityProducer {

    private static UserActivityProducer instance = null;

    /**
     * Singleton
     * @return
     */
    public static UserActivityProducer getInstance() {
        if (instance == null) {
            instance = new UserActivityProducer();
        }
        return instance;
    }


    /**
     * This method creates Kafka Producer object and writes the messages to kafka topic
     * @param coordinateX
     * @param coordinateY
     */
    public void produce(String coordinateX, String coordinateY) {

        /*  Essential configuration proeprties for producer.
            Please refer below link for more details on these properties
            http://kafka.apache.org/08/configuration.html
         */

        Properties props = new Properties();
        /*
        The first property, “metadata.broker.list” defines where the Producer can find a one or more Brokers to
        determine the Leader for each topic. This does not need to be the full set of Brokers in your cluster
        but should include at least two in case the first Broker is not available. No need to worry about figuring
        out which Broker is the leader for the topic (and partition), the Producer knows how to connect to the Broker
         and ask for the meta data then connect to the correct Broker
         */
        props.put("metadata.broker.list", "localhost:9092");
        /*
        The second property “serializer.class” defines what Serializer to use when preparing the message for
        transmission to the Broker. In our example we use a simple String encoder provided as part of Kafka.
         */
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        /**
         * The last property "request.required.acks" tells Kafka that you want your Producer to require an acknowledgement
         * from the Broker that the message was received. Without this setting the Producer will 'fire and forget'
         * possibly leading to data loss
         */
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        // Construct a message to write and convert to KeyedMessage to write to topic
        String msg = System.currentTimeMillis() + "," + coordinateX + "," + coordinateY;
        KeyedMessage<String, String> data = new KeyedMessage<String, String>("useractivity", msg);
        producer.send(data);
        producer.close();
    }
}
