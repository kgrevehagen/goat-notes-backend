package com.kgrevehagen.goatnotes.db

import com.kgrevehagen.goatnotes.notes.model.NoteEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
internal class DynamoDbConfig(
    @Value("\${aws.region}")
    private val awsRegion: String
) {

    @Bean
    @Profile("!dev")
    fun dynamoDbClientProd(): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }

    @Bean
    @Profile("dev")
    fun dynamoDbClientDev(
        @Value("\${aws.dynamodb.endpoint}")
        endpoint: String
    ): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("fake", "fake")))
            .build()
    }

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbClient: DynamoDbClient): DynamoDbEnhancedClient {
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
    }

    @Bean
    fun notesTable(db: DynamoDbEnhancedClient): DynamoDbTable<NoteEntity> {
        return db.table(TABLE_NAME_NOTES, TableSchema.fromBean(NoteEntity::class.java))
    }

    companion object {
        const val TABLE_NAME_NOTES = "Notes"
    }
}
