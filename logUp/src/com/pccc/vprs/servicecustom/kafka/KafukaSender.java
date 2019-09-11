package com.pccc.vprs.servicecustom.kafka;

import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSONObject;

public class KafukaSender {

private SleepInBetween sleepPolicy;
    private Producer<String, String> producer;
  //  private String topic;
    private String topic ="bams_vprs";
    
    
    public KafukaSender(String topic)
    {
        this.sleepPolicy = new SleepInBetween();
        this.producer = KafukaProducer.createKafkaProducer();
        this.topic = topic;
    }
    
    public void accept(List<JSONObject> batchJsonList)
    {
        sleepInBetween(sleepPolicy, 1000);
        sendToKafka(batchJsonList);
    }
    
    private void sleepInBetween(SleepInBetween sleepPolicy, long maxSleepTime)
    {
        sleepPolicy.sleepAtMax(maxSleepTime);
    }
    
    private void sendToKafka(List<JSONObject> jsonList)
    {
//        jsonList.forEach(jsonObj -> System.out.println(jsonObj));
//        jsonList.forEach(jsonObj -> producer.send(new ProducerRecord<>(topic, jsonObj.toJSONString())));
    	for(int i=0;i<jsonList.size();i++)
    	{
    //	producer.send(new ProducerRecord<String,String>(topic, jsonList.get(i).toJSONString()));
    	producer.send(new ProducerRecord<String,String>("bams_vprs", jsonList.get(i).toJSONString()));
    	}
        producer.flush();
        System.out.println("sent to kafka with data size:" + jsonList.size());
    }
}