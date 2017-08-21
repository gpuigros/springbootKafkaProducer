package com.puigros.core.messaging;


import com.puigros.core.messaging.kafka.KafkaChannelsOutput;
import com.puigros.dto.DTO;
import org.springframework.context.ApplicationEvent;



import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by gpuigros on 21/08/17.
 *  * The Class MessagingDTOEvent.
 *  *
 *  * @param <T> the entity type
 *
 *
 * @param <T> the generic type
 */

/*
 * (non-Javadoc)
 *
 * @see java.util.EventObject#toString()
 */
@Data

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#hashCode()
 */
@EqualsAndHashCode(callSuper = false)
public class MessagingDTOEvent<T extends DTO<?>> extends ApplicationEvent {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The action. */
    private final MessagingDTOActions action;

    /** The destination. */
    private final KafkaChannelsOutput destination;

    /** The id. */
    private String id;

    /**
     *      * Instantiates a new insert entity event.
     *
     * @param source the source
     * @param action the action
     * @param destination the destination
     * @param id the id
     */
    public MessagingDTOEvent(final T source, final MessagingDTOActions action, final KafkaChannelsOutput destination, final String id) {
        super(source);
        this.source = source;
        this.action = action;
        this.destination = destination;
        this.id = id;
    }
}