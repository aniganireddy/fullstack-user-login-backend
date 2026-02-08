package com.example.fullstack.service.kafka;

import com.example.fullstack.events.AccountCreationEvent;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventProducer {

    private static final String TOPIC = "account-created-topic";
    private final KafkaTemplate<String, AccountCreationEvent> kafkaTemplate;

    public void sendEvent(AccountCreationEvent event){
        kafkaTemplate.send(TOPIC,event.getPartyId(),event);
    }
}
