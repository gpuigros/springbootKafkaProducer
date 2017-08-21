package com.puigros.core.messaging.kafka;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.puigros.core.messaging.MessagingDTOEvent;
import com.puigros.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;



import lombok.extern.slf4j.Slf4j;

/**
 * Class that processes MessageDTOEvent
 * Created by gpuigros on 21/08/17.
 */
@Component
@Slf4j
@EnableBinding
public class KafkaMessageListener {

    @Autowired
    private BinderAwareChannelResolver binderAwareChannelResolver;

    /**
     * The Constant LISTENER_CONDITION.
     */

    private static final String LISTENER_CONDITION =
            "#event.source instanceof T(com.puigros.dto.DTO)";

    /**
     * On message dto event.
     *
     * @param event the event
     */
    @TransactionalEventListener(condition = LISTENER_CONDITION, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void afterEvent(final MessagingDTOEvent<?> event) {
        try {
            final DTO dto = (DTO) event.getSource();
            final String action = event.getAction().getCode();
            final String destination = event.getDestination().getCode();
            final Map<String, Object> headersMap = new HashMap();
            headersMap.put(KafkaConstants.ENTITY_ID.getCode(), dto.getId());
            headersMap.put(KafkaConstants.ACTION_CODE.getCode(), action);
            headersMap.put(KafkaConstants.ENTITY_NAME.getCode(), dto.getClass().getCanonicalName());
            headersMap.put(KafkaHeaders.MESSAGE_KEY, event.getId().getBytes(StandardCharsets.UTF_8));

            final Message message = MessageBuilder.withPayload(dto).copyHeaders(headersMap).build();
            final MessageChannel messageChannel = binderAwareChannelResolver.resolveDestination(destination);

            log.debug("[KAFKA] Sending message to queue {} value: {}", destination, message.getPayload());

            messageChannel.send(message);
        } catch (final Exception e) {
            log.error("[KAFKA] Error produced sending: {} to topic: {} exception: {}", event.getAction().getCode(), event.getDestination().getCode(),
                    e);
            if (log.isDebugEnabled()) {
                log.debug("[KAFKA] Error exception: {}", e);
            }
        }
    }
}
