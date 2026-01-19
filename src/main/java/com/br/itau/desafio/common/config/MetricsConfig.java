package com.br.itau.desafio.common.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter sqsMessagesConsumedCounter(MeterRegistry meterRegistry) {
        return Counter.builder("sqs_listener_messages_consumed_total")
                .description("Total messages consumed by SQS listener")
                .register(meterRegistry);
    }
}
