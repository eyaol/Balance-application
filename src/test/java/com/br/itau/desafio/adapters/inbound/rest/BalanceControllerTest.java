package com.br.itau.desafio.adapters.inbound.rest;

import com.br.itau.desafio.adapters.inbound.rest.dto.ApiResponseDTO;
import com.br.itau.desafio.adapters.inbound.rest.mapper.ResponseMapper;
import com.br.itau.desafio.application.usecase.GetBalanceUseCase;
import com.br.itau.desafio.common.entity.BalanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceControllerTest {
    @Mock
    private ResponseMapper responseMapper;
    @Mock
    private GetBalanceUseCase useCase;
    @InjectMocks
    private BalanceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBalance_shouldReturnApiResponseDTO_whenAccountExists() {
        String accountId = "acc123";
        BalanceEntity entity = BalanceEntity.builder()
                .accountId(accountId)
                .ownerId("owner456")
                .amount(new BigDecimal("150.75"))
                .currency("BRL")
                .updatedAt(1768732848732604L)
                .build();
        ApiResponseDTO dto = new ApiResponseDTO(
                accountId,
                "owner456",
                new ApiResponseDTO.BalanceDTO(new BigDecimal("150.75"), "BRL"),
                "2026-01-18T16:20:48.732604-03:00"
        );
        when(useCase.getBalance(accountId)).thenReturn(entity);
        when(responseMapper.toBalanceResponse(entity)).thenReturn(dto);
        ResponseEntity<ApiResponseDTO> response = controller.getBalance(accountId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }
}
