package com.br.itau.desafio.adapters.inbound.rest.mapper;

import com.br.itau.desafio.adapters.inbound.rest.dto.ApiResponseDTO;
import com.br.itau.desafio.common.entity.BalanceEntity;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ResponseMapperTest {
    @Test
    void toBalanceResponse_shouldMapEntityToApiResponseDTO() {
        BalanceEntity entity = BalanceEntity.builder()
                .accountId("acc123")
                .ownerId("owner456")
                .amount(new BigDecimal("150.75"))
                .currency("BRL")
                .updatedAt(1768732848732604L) // microssegundos
                .build();
        ResponseMapper mapper = new ResponseMapper();
        ApiResponseDTO dto = mapper.toBalanceResponse(entity);
        assertEquals("acc123", dto.id());
        assertEquals("owner456", dto.owner());
        assertEquals(new BigDecimal("150.75"), dto.balance().amount());
        assertEquals("BRL", dto.balance().currency());
    }
}
