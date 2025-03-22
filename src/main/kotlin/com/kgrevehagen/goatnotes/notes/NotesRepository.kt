package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.NoteEntity
import com.kgrevehagen.goatnotes.notes.model.USER_ID_CREATED_AT_INDEX_NAME
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional

internal interface NotesRepository {
    fun add(noteEntity: NoteEntity)
    fun getAllNotesForUser(userId: String): List<NoteEntity>
}

@Repository
internal class DefaultNotesRepository(private val table: DynamoDbTable<NoteEntity>) : NotesRepository {

    override fun add(noteEntity: NoteEntity) = table.putItem(noteEntity)

    override fun getAllNotesForUser(userId: String): List<NoteEntity> {
        return table.index(USER_ID_CREATED_AT_INDEX_NAME)
            .query { r ->
                r.queryConditional(QueryConditional.keyEqualTo { k -> k.partitionValue(userId) })
                    .scanIndexForward(false)
            }.flatMap { it.items() }
    }
}