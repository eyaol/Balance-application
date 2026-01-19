package com.br.itau.desafio.adapters.inbound.rest.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ApiResponseDTO(
        String id,
        String owner,
        BalanceDTO balance,
        String updatedAt
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record BalanceDTO(
            BigDecimal amount,
            String currency
    ) {
    }
}
