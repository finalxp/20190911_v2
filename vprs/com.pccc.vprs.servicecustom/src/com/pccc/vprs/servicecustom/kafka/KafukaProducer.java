package com.pccc.vprs.servicecustom.kafka;


//import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import com.pccc.touda.common.util.ConfigUtils;


public class KafukaProducer {
	
    private static Producer<String, String> kafkaProducer;
    
    private KafukaProducer()
    {

    };
    
    public static synchronized Producer<String, String> createKafkaProducer() {
        if(kafkaProducer == null)
        {
        	kafkaProducer = new KafkaProducer<String, String>(assembleProperties());
        }
        
        return kafkaProducer;
    }

    public static  Properties assembleProperties()
    {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigUtils.getProperty("stream.ip"));
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "182.180.197.18:9200");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);//a batch size of zero will disable batching entirely
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);//send message without delay
        props.put(ProducerConfig.ACKS_CONFIG, "all");//对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

 
        return props;
    }

//    public void sendKafka(String topic, List<String> lines) throws InterruptedException, ExecutionException
//    {
//        for(String line : lines)
//        {
//            ProducerRecord<String, String> message = new ProducerRecord<String, String>(topic, line);
//            this.kafkaProducer.send(message).get();        
//        }
//        
//    }


}
