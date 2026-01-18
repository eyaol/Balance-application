package com.br.itau.desafio.adapters.inbound.rest.dto;

import java.math.BigDecimal;

public record BalanceResponseDTO(
        String id,
        String owner,
        BalanceDTO balance,
        String updated_at
) {
    public record BalanceDTO(
            BigDecimal amount,
            String currency
    ) {
    }
}
