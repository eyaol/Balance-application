package com.br.itau.desafio.adapters.outbound.dynamodb;

import com.br.itau.desafio.application.port.outbound.DynamoDbRepository;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DynamoDbAdapter implements DynamoDbRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbAdapter.class);

    private final DynamoDbAsyncTable<BalanceEntity> balanceTableAsync;
    private final DynamoDbTable<BalanceEntity> balanceTable;
    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    @Value("${balance.table.name}")
    private String balanceTableName;

    /**
     * Tenta salvar uma nova entidade de saldo no DynamoDB.
     * Se o item já existir, dispara uma atualização apenas dos campos (amount, updatedAt, transactionId)
     * caso o novo updatedAt seja mais recente que o atual.
     *
     * @param entity Entidade de saldo a ser salva ou atualizada.
     */
    @Override
    public void saveBalance(BalanceEntity entity) {
        PutItemEnhancedRequest<BalanceEntity> request = buildPutRequest(entity);
        balanceTableAsync.putItem(request).whenComplete((result, throwable) -> {
            if (throwable == null) {
                LOGGER.info("Balance saved successfully: {}", entity);
            } else if (throwable.getCause() instanceof ConditionalCheckFailedException) {
                LOGGER.warn("Updating balance: {}", entity);
                updateBalanceFields(entity);
            } else {
                LOGGER.error("Error saving balance to DynamoDB", throwable);
            }
        });
    }

    /**
     * Monta um PutItemEnhancedRequest com condição para inserir apenas se o item não existir.
     *
     * @param entity Entidade de saldo a ser inserida.
     * @return PutItemEnhancedRequest configurado.
     */
    private PutItemEnhancedRequest<BalanceEntity> buildPutRequest(BalanceEntity entity) {
        return PutItemEnhancedRequest.builder(BalanceEntity.class)
                .item(entity)
                .conditionExpression(
                        Expression.builder()
                                .expression("attribute_not_exists(account_id) AND attribute_not_exists(owner_id)")
                                .build()
                ).build();
    }

    /**
     * Atualiza apenas os campos amount, updatedAt e transactionId de um saldo existente,
     * mas somente se o novo updatedAt for mais recente que o atual no banco.
     *
     * @param entity Entidade de saldo com valores atualizados.
     */
    private void updateBalanceFields(BalanceEntity entity) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("account_id", AttributeValue.builder().s(entity.getAccountId()).build());
        key.put("owner_id", AttributeValue.builder().s(entity.getOwnerId()).build());
        String updateExpression = "SET amount = :amount, updated_at = :updatedAt, transaction_id = :transactionId";
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":amount", AttributeValue.builder().n(entity.getAmount().toString()).build());
        expressionAttributeValues.put(":updatedAt", AttributeValue.builder().n(Long.toString(entity.getUpdatedAt())).build());
        expressionAttributeValues.put(":transactionId", AttributeValue.builder().s(entity.getTransactionId()).build());
        String conditionExpression = "updated_at < :newTimestamp";
        expressionAttributeValues.put(":newTimestamp", AttributeValue.builder().n(Long.toString(entity.getUpdatedAt())).build());
        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(balanceTableName)
                .key(key)
                .updateExpression(updateExpression)
                .conditionExpression(conditionExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();
        dynamoDbAsyncClient.updateItem(request).whenComplete((updateResult, updateThrowable) -> {
            if (updateThrowable == null) {
                LOGGER.info("Balance updated successfully: accountId={}, ownerId={}, amount={}", entity.getAccountId(), entity.getOwnerId(), entity.getAmount());
            } else if (updateThrowable.getCause() instanceof ConditionalCheckFailedException) {
                LOGGER.warn("Update ignored: updatedAt is not newer. accountId={}, ownerId={}", entity.getAccountId(), entity.getOwnerId());
            } else {
                LOGGER.error("Error updating balance in DynamoDB", updateThrowable);
            }
        });
    }


    /**
     * Recupera o primeiro saldo do DynamoDB pelo accountId (partition key), usando query com leitura consistente.
     *
     * @param accountId Chave de partição da entidade de saldo.
     * @return O primeiro BalanceEntity encontrado, ou null se não existir.
     */
    @Override
    public BalanceEntity getBalanceByAccountId(String accountId) {
        try {
            var results = balanceTable.query(r -> r
                    .queryConditional(
                            QueryConditional.keyEqualTo(
                                    Key.builder().partitionValue(accountId).build())
                    )
                    .consistentRead(true)
            ).items();
            BalanceEntity entity = results.iterator().hasNext() ? results.iterator().next() : null;
            if (entity != null) {
                LOGGER.info("Balance retrieved successfully for accountId: {}: {}", accountId, entity);
            } else {
                LOGGER.warn("No balance found for accountId: {}", accountId);
            }
            return entity;
        } catch (Exception e) {
            LOGGER.error("Error retrieving balance from DynamoDB", e);
            throw e;
        }
    }

}
