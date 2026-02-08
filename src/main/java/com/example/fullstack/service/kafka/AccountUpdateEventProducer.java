package com.example.fullstack.service.kafka;

import com.example.fullstack.events.AccountUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUpdateEventProducer {

    private static final String TOPIC = "account-updated-topic";

    private final KafkaTemplate<String, AccountUpdateEvent> kafkaTemplate;

    public void sendUpdateEvent(AccountUpdateEvent event){
        kafkaTemplate.send(TOPIC,event.getPartyId(),event);

    }
}
