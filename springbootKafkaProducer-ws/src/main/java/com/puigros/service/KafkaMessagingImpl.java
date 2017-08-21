package com.puigros.service;

import com.puigros.core.messaging.MessagingDTOActions;
import com.puigros.core.messaging.MessagingDTOEvent;
import com.puigros.core.messaging.kafka.KafkaChannelsOutput;
import com.puigros.dto.TestDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * Created by gpuigros on 21/08/17.
 */
@Service("KafkaMessaging")
public class KafkaMessagingImpl implements KafkaMessaging, ApplicationEventPublisherAware {
    /** The application event publisher. */
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void sendMessage(TestDTO testDTO){
        sendMessageToSuscribers(MessagingDTOActions.CREATE, testDTO);
    }

    protected void sendMessageToSuscribers(final MessagingDTOActions action, final TestDTO testDTO) {
        applicationEventPublisher.publishEvent(new MessagingDTOEvent<>(testDTO, action, KafkaChannelsOutput.HOTELS, testDTO.getId()));
    }


    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
