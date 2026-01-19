
import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.application.mapper.BalanceMapper;
import com.br.itau.desafio.common.entity.BalanceEntity;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class BalanceMapperTest {
    @Test
    void mapToEntity_shouldMapFieldsCorrectly() {
        var balance = new MessageBodyDTO.Balance(new BigDecimal("100.00"), "BRL");
        var account = new MessageBodyDTO.Account("acc1", "owner1", "2026-01-18T10:00:00", "ACTIVE", balance);
        var transaction = new MessageBodyDTO.Transaction("tx1", "PIX", new BigDecimal("100.00"), "BRL", "DONE", 123456789L);
        var dto = new MessageBodyDTO(transaction, account);
        BalanceEntity entity = BalanceMapper.mapToEntity(dto);
        assertEquals("acc1", entity.getAccountId());
        assertEquals("owner1", entity.getOwnerId());
        assertEquals(new BigDecimal("100.00"), entity.getAmount());
        assertEquals("BRL", entity.getCurrency());
        assertEquals("tx1", entity.getTransactionId());
        assertEquals(123456789L, entity.getUpdatedAt());
    }
}
