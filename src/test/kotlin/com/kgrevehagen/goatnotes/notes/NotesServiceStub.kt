package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import com.kgrevehagen.goatnotes.notes.model.NoteDto

internal class NotesServiceStub : NotesService {

    override fun add(userId: String, noteRequest: CreateNoteRequest): NoteDto {
        return NoteDto(userId, "1", noteRequest.noteText, 1)
    }

    override fun getAllNotesForUser(userId: String): List<NoteDto> {
        return when (userId) {
            "user-id-empty" -> listOf()
            else -> listOf(NoteDto(userId, "1", "note text", 1))
        }
    }
}