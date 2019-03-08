package com.alany.other.kafka;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
///**
// * Created by yinxing on 2018/7/9.
// * 消息消费者
// */
//@Component
//public class KafkaConsumer {
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    @KafkaListener(topics = {"bootkafka"})
//    public void listen(String data) {
//        logger.info("收到kafka消息:" + data);
//    }
//
//    /*public void listen(ConsumerRecord<?, ?> record) {
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//
//            log.info("record:" + record);
//            log.info("message:" + message);
//        }
//    }*/
//}
