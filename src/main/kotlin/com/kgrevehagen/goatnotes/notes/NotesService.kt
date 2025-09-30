package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import com.kgrevehagen.goatnotes.notes.model.NoteDto
import com.kgrevehagen.goatnotes.notes.model.NoteEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.util.UUID

internal interface NotesService {
    fun add(userId: String, noteRequest: CreateNoteRequest): NoteDto
    fun getAllNotesForUser(userId: String): List<NoteDto>
    fun deleteNoteForUser(userId: String, noteId: String): NoteDto
}

@Service
internal class DefaultNotesService(
    private val notesRepository: NotesRepository,
    private val noteEntityFactory: NoteEntity.Factory,
) : NotesService {

    @PreAuthorize("#userId == authentication.name")
    override fun add(userId: String, noteRequest: CreateNoteRequest): NoteDto {
        val noteEntity = noteEntityFactory.create(
            userId,
            UUID.randomUUID().toString(),
            noteRequest.noteText,
            System.currentTimeMillis()
        )
        notesRepository.add(noteEntity)
        return noteEntity.toDto()
    }

    @PreAuthorize("#userId == authentication.name")
    override fun getAllNotesForUser(userId: String): List<NoteDto> {
        return notesRepository.getAllNotesForUser(userId).toDto()
    }

    @PreAuthorize("#userId == authentication.name")
    override fun deleteNoteForUser(userId: String, noteId: String): NoteDto {
        return notesRepository.deleteNoteForUser(userId, noteId).toDto()
    }
}

private fun NoteEntity.toDto(): NoteDto {
    return NoteDto(userId, noteId, noteText, createdAt)
}

private fun List<NoteEntity>.toDto(): List<NoteDto> {
    return map { it.toDto() }
}
