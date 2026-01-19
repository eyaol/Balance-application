package com.br.itau.desafio.adapters.inbound.message;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.application.usecase.UpdateBalanceUseCase;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;

class SqsListenerAdapterTest {
//    private UpdateBalanceUseCase useCase;
//    private CircuitBreakerRegistry registry;
//    private CircuitBreaker circuitBreaker;
//    private SqsListenerAdapter adapter;
//    private Acknowledgement acknowledgement;
//
//    @BeforeEach
//    void setUp() {
//        useCase = mock(UpdateBalanceUseCase.class);
//        registry = mock(CircuitBreakerRegistry.class);
//        circuitBreaker = mock(CircuitBreaker.class);
//        acknowledgement = mock(Acknowledgement.class);
//        when(registry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
//        doAnswer(invocation -> {
//            Runnable runnable = invocation.getArgument(0);
//            runnable.run();
//            return null;
//        }).when(circuitBreaker).executeRunnable(any(Runnable.class));
//        adapter = new SqsListenerAdapter(useCase, registry);
//    }
//
//    @Test
//    void handleMessage_shouldCallUpdateBalance_whenValidJson() {
//        String json = """
//                {
//                  "transaction": {
//                    "id": "tx1",
//                    "type": "PIX",
//                    "amount": 100.00,
//                    "currency": "BRL",
//                    "status": "DONE",
//                    "timestamp": 123456789
//                  },
//                  "account": {
//                    "id": "acc1",
//                    "owner": "owner1",
//                    "created_at": "2026-01-18T10:00:00",
//                    "status": "ACTIVE",
//                    "balance": {
//                      "amount": 100.00,
//                      "currency": "BRL"
//                    }
//                  }
//                }
//                """;
//        adapter.handleMessage(json, acknowledgement);
//        verify(useCase, times(1)).updateBalance(any(MessageBodyDTO.class));
//    }
//
//    @Test
//    void handleMessage_shouldLogError_whenInvalidJson() {
//        String invalidJson = "{";
//        assertDoesNotThrow(() -> adapter.handleMessage(invalidJson, acknowledgement));
//        verify(useCase, never()).updateBalance(any());
//    }
}
