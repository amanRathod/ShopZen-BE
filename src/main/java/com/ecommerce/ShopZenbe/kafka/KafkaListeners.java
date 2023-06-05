package com.ecommerce.ShopZenbe.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaListeners {
  
  @KafkaListener(topics = "topic14", groupId = "group_id")
  void listener(String data) {
    log.info("Consumed message: ======================" + data + ":emojies 😀😀😀😀😀");
  }
}
