package com.br.itau.desafio.adapters.inbound.message.mapper;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe utilitária responsável por converter mensagens JSON recebidas da SQS
 * em instâncias de MessageBodyDTO.
 * Utiliza Jackson para desserialização.
 *
 * @since 2026-01-18
 */
public class MessageMapper {

    /**
     * Converte uma string JSON em um objeto MessageBodyDTO.
     *
     * @param message JSON recebido da SQS
     * @return objeto MessageBodyDTO correspondente ao JSON
     * @throws RuntimeException se ocorrer erro de desserialização
     */
    public static MessageBodyDTO parse(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, MessageBodyDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing SQS message", e);
        }
    }

}
