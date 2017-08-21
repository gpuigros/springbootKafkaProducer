package com.puigros.service;

import com.puigros.dto.TestDTO;

/**
 * Created by gpuigros on 21/08/17.
 */
public interface KafkaMessaging {
    void sendMessage(TestDTO testDTO);
}
