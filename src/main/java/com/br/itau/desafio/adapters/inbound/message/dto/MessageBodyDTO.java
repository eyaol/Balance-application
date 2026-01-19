package com.br.itau.desafio.adapters.inbound.message.dto;

import java.math.BigDecimal;

public record MessageBodyDTO(
    Transaction transaction,
    Account account
) {
    public record Transaction(
        String id,
        String type,
        BigDecimal amount,
        String currency,
        String status,
        long timestamp
    ) {}

    public record Account(
        String id,
        String owner,
        String created_at,
        String status,
        Balance balance
    ) {}

    public record Balance(
        BigDecimal amount,
        String currency
    ) {}
}
