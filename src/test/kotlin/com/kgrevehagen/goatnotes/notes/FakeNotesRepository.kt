package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.NoteEntity

internal class FakeNotesRepository : NotesRepository {

    private val notes = mutableListOf<NoteEntity>()

    override fun add(noteEntity: NoteEntity) {
        notes.add(noteEntity)
    }

    override fun getAllNotesForUser(userId: String): List<NoteEntity> {
        return notes.filter { it.userId == userId }
    }
}