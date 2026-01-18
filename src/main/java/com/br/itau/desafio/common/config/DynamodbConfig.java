package com.br.itau.desafio.common.config;

import com.br.itau.desafio.common.entity.BalanceEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamodbConfig {

    @Value("${spring.cloud.aws.dynamodb.endpoint}")
    private String dynamoEndpoint;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Value("${balance.table.name}")
    private String balanceTableName;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(dynamoEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")
                ))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<BalanceEntity> balanceAsyncTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table(balanceTableName, BalanceEntity.getTableSchema());
    }

    @Bean
    public DynamoDbTable<BalanceEntity> balanceTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(balanceTableName, BalanceEntity.getTableSchema());
    }
}
