package com.br.itau.desafio.adapters.inbound.message;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.adapters.inbound.message.mapper.MessageMapper;
import com.br.itau.desafio.application.usecase.UpdateBalanceUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import io.micrometer.core.instrument.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adaptador responsável por escutar mensagens da fila SQS 'transacoes-financeiras-processadas'.
 * Ao receber uma mensagem, converte o payload para MessageBodyDTO e aciona o caso de uso de atualização de saldo.
 * Em caso de erro, registra o ocorrido no log.
 */
@RequiredArgsConstructor
@Component
public class SqsListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsListenerAdapter.class);
    private final UpdateBalanceUseCase useCase;
    private final CircuitBreakerRegistry registry;
    private final Counter messagesConsumedCounter;

    /**
     * Processa mensagens recebidas da fila SQS 'transacoes-financeiras-processadas'.
     *
     * @param message JSON recebido da fila SQS.
     */
    @SqsListener(
            value = "${aws.sqs.queue-name}",
            maxConcurrentMessages = "${aws.sqs.listener.concurrency}",
            maxMessagesPerPoll = "${aws.sqs.listener.max-number-of-messages}",
            pollTimeoutSeconds = "${aws.sqs.listener.wait-time-seconds}",
            messageVisibilitySeconds = "${aws.sqs.listener.visibility-timeout-seconds}",
            acknowledgementMode = "MANUAL"
    )
    public void handleMessage(String message, Acknowledgement ack) {

        CircuitBreaker circuitBreaker = registry.circuitBreaker("saveBalance");

        if (circuitBreaker.getState() == CircuitBreaker.State.OPEN) {
            LOGGER.warn("Circuit OPEN — message will be retried");
            return;
        }

        try {
            MessageBodyDTO messageDto = MessageMapper.parse(message);

            circuitBreaker.executeRunnable(() ->
                    useCase.updateBalance(messageDto)
            );

            ack.acknowledge();
            messagesConsumedCounter.increment();

        } catch (Exception e) {
            LOGGER.error(
                    "Error processing SQS message. Will retry. Error: {}",
                    e.getMessage(),
                    e
            );
        }
    }

}
