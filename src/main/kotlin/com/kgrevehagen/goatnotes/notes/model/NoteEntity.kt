package com.kgrevehagen.goatnotes.notes.model

import org.springframework.stereotype.Component
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
internal data class NoteEntity(
    @get:DynamoDbPartitionKey
    var userId: String,
    @get:DynamoDbSortKey
    var noteId: String,
    var noteText: String,
    @get:DynamoDbSecondarySortKey(indexNames = [USER_ID_CREATED_AT_INDEX_NAME])
    var createdAt: Long,
) {
    constructor() : this("", "", "", 0)

    @Component
    companion object Factory {
        fun create(userId: String, noteId: String, noteText: String, createdAt: Long): NoteEntity {
            return NoteEntity(userId, noteId, noteText, createdAt)
        }
    }
}

internal const val USER_ID_CREATED_AT_INDEX_NAME = "userId-createdAt-index"
