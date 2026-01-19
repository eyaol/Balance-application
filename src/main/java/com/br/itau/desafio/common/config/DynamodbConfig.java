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
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.time.Duration;

/**
 * Configuração dos beans necessários para integração com o DynamoDB.
 * Define clientes síncronos e assíncronos, além das tabelas para a entidade BalanceEntity.
 *
 * @since 2026-01-18
 */
@Configuration
public class DynamodbConfig {

    /**
     * Endpoint do DynamoDB configurado via properties.
     */
    @Value("${aws.dynamodb.endpoint}")
    private String dynamoEndpoint;

    /**
     * Região AWS configurada via properties.
     */
    @Value("${aws.region}")
    private String region;

    @Value("${aws.dynamodb.http-client.max-concurrency:200}")
    private int maxConcurrency;

    @Value("${aws.dynamodb.http-client.max-pending-connection-acquires:20000}")
    private int maxPendingConnectionAcquires;

    @Value("${aws.dynamodb.http-client.connection-acquisition-timeout:30s}")
    private Duration connectionAcquisitionTimeout;

    private static final String TB_NAME = "tb_balance";

    /**
     * Cria o cliente assíncrono do DynamoDB.
     * @return DynamoDbAsyncClient configurado
     */
    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .httpClientBuilder(NettyNioAsyncHttpClient.builder()
                        .maxConcurrency(maxConcurrency)
                        .maxPendingConnectionAcquires(maxPendingConnectionAcquires)
                        .connectionAcquisitionTimeout(connectionAcquisitionTimeout)
                )
                .endpointOverride(URI.create(dynamoEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")
                ))
                .build();
    }

    /**
     * Cria o cliente síncrono do DynamoDB.
     * @return DynamoDbClient configurado
     */
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(dynamoEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")
                ))
                .build();
    }

    /**
     * Cria o cliente assíncrono aprimorado do DynamoDB.
     * @param dynamoDbAsyncClient cliente assíncrono base
     * @return DynamoDbEnhancedAsyncClient configurado
     */
    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    /**
     * Cria o cliente síncrono aprimorado do DynamoDB.
     * @param dynamoDbClient cliente síncrono base
     * @return DynamoDbEnhancedClient configurado
     */
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    /**
     * Cria a tabela assíncrona para a entidade BalanceEntity.
     * @param dynamoDbEnhancedAsyncClient cliente aprimorado assíncrono
     * @return tabela assíncrona de BalanceEntity
     */
    @Bean
    public DynamoDbAsyncTable<BalanceEntity> balanceAsyncTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table(TB_NAME, BalanceEntity.getTableSchema());
    }

    /**
     * Cria a tabela síncrona para a entidade BalanceEntity.
     * @param dynamoDbEnhancedClient cliente aprimorado síncrono
     * @return tabela síncrona de BalanceEntity
     */
    @Bean
    public DynamoDbTable<BalanceEntity> balanceTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(TB_NAME, BalanceEntity.getTableSchema());
    }
}
