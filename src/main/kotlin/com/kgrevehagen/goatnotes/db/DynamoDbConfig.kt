package com.kgrevehagen.goatnotes.db

import com.kgrevehagen.goatnotes.notes.model.NoteEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Configuration
internal class DynamoDbConfig {

    @Value("\${aws.region}")
    private lateinit var awsRegion: String

    @Value("\${aws.profile}")
    private lateinit var awsProfile: String

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .credentialsProvider(ProfileCredentialsProvider.create(awsProfile))
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
