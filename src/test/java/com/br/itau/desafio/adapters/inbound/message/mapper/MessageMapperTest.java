package com.br.itau.desafio.adapters.inbound.message.mapper;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class MessageMapperTest {
    @Test
    void parse_shouldReturnMessageBodyDTO_whenValidJson() {
        String json = """
        {
          "transaction": {
            "id": "tx1",
            "type": "CREDIT",
            "amount": 100.00,
            "currency": "BRL",
            "status": "DONE",
            "timestamp": 123456789
          },
          "account": {
            "id": "acc1",
            "owner": "owner1",
            "created_at": "2026-01-18T10:00:00",
            "status": "ACTIVE",
            "balance": {
              "amount": 100.00,
              "currency": "BRL"
            }
          }
        }
        """;
        MessageBodyDTO dto = MessageMapper.parse(json);
        assertNotNull(dto);
        assertEquals("tx1", dto.transaction().id());
        assertEquals("CREDIT", dto.transaction().type());
        assertEquals(new BigDecimal("100.00"), dto.transaction().amount());
        assertEquals("BRL", dto.transaction().currency());
        assertEquals("DONE", dto.transaction().status());
        assertEquals(123456789L, dto.transaction().timestamp());
        assertEquals("acc1", dto.account().id());
        assertEquals("owner1", dto.account().owner());
        assertEquals("2026-01-18T10:00:00", dto.account().created_at());
        assertEquals("ACTIVE", dto.account().status());
        assertEquals(new BigDecimal("100.00"), dto.account().balance().amount());
        assertEquals("BRL", dto.account().balance().currency());
    }

    @Test
    void parse_shouldThrowRuntimeException_whenInvalidJson() {
        String invalidJson = "{";
        assertThrows(RuntimeException.class, () -> MessageMapper.parse(invalidJson));
    }
}
