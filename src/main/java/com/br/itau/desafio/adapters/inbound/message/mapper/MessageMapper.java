package com.br.itau.desafio.adapters.inbound.message.mapper;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageMapper {

    public static MessageBodyDTO parse(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, MessageBodyDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing SQS message", e);
        }
    }

}
