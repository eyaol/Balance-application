package com.br.itau.desafio.application.usecase;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.application.mapper.BalanceMapper;
import com.br.itau.desafio.application.port.outbound.DynamoDbRepository;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por atualizar o saldo de uma conta.
 * Utiliza o repositório DynamoDbRepository para persistência dos dados.
 *
 * @since 2026-01-18
 */
@RequiredArgsConstructor
@Component
public class UpdateBalanceUseCase {

    private final DynamoDbRepository dynamoDbRepository;

    /**
     * Atualiza o saldo a partir de um DTO de mensagem.
     * @param messageBodyDTO DTO da mensagem recebida
     */
    public void updateBalance(MessageBodyDTO messageBodyDTO) {
        BalanceEntity entity = BalanceMapper.mapToEntity(messageBodyDTO);
        dynamoDbRepository.saveBalance(entity);
    }

}
