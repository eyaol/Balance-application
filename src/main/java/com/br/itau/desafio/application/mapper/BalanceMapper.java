package com.br.itau.desafio.application.mapper;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.common.entity.BalanceEntity;

public class BalanceMapper {

    public static BalanceEntity mapToEntity(MessageBodyDTO messageBodyDTO) {
        return BalanceEntity.builder()
                .accountId(messageBodyDTO.account().id())
                .ownerId(messageBodyDTO.account().owner())
                .amount(messageBodyDTO.account().balance().amount())
                .currency(messageBodyDTO.account().balance().currency())
                .transactionId(messageBodyDTO.transaction().id())
                .updatedAt(messageBodyDTO.transaction().timestamp())
                .build();
    }

}
