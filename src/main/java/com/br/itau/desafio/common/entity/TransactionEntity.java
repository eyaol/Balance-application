package com.br.itau.desafio.common.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class TransactionEntity {

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("transaction_id")})
    private String transactionId;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("account_id")})
    private String accountId;



}
