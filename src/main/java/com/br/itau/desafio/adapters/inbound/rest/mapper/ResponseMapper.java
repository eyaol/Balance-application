package com.br.itau.desafio.adapters.inbound.rest.mapper;

import com.br.itau.desafio.adapters.inbound.rest.dto.ApiResponseDTO;
import com.br.itau.desafio.common.entity.BalanceEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ResponseMapper {

    public ApiResponseDTO toBalanceResponse(BalanceEntity entity) {
        return new ApiResponseDTO(
                entity.getAccountId(),
                entity.getOwnerId(),
                new ApiResponseDTO.BalanceDTO(entity.getAmount(), entity.getCurrency()),
                formatTimestamp(entity.getUpdatedAt())
        );
    }

    private String formatTimestamp(long timestamp) {
        // Converte microssegundos para Instant (segundos + nanossegundos)
        long seconds = timestamp / 1_000_000;
        long nanos = (timestamp % 1_000_000) * 1_000;
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        OffsetDateTime dateTime = instant.atZone(java.time.ZoneId.of("America/Sao_Paulo")).toOffsetDateTime();
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
