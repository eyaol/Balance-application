package com.br.itau.desafio.adapters.inbound.message;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.adapters.inbound.message.mapper.MessageMapper;
import com.br.itau.desafio.application.usecase.UpdateBalanceUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SqsListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsListenerAdapter.class);
    private final UpdateBalanceUseCase useCase;

    @SqsListener(value = "transacoes-financeiras-processadas")
    public void handleMessage(String message) {
        try {
            MessageBodyDTO messageDto = MessageMapper.parse(message);
            useCase.updateBalance(messageDto);
        } catch (Exception e) {
            LOGGER.error("Error processing SQS message: {}. Error: {}", message, e.getMessage(), e);
        }
    }

}
