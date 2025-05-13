package com.kgrevehagen.goatnotes.notes

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import com.kgrevehagen.goatnotes.notes.model.NoteEntity
import org.junit.jupiter.api.Test

class NotesServiceTest {

    private val notesRepository = FakeNotesRepository()
    private val noteEntityFactory = NoteEntity.Factory()
    private val notesService = DefaultNotesService(notesRepository, noteEntityFactory)

    @Test
    fun `adds note`() {
        val userId = "userId"
        val noteText = "noteText"
        val notesBefore = notesRepository.getAllNotesForUser(userId)

        val addedNote = notesService.add(userId, CreateNoteRequest(noteText))

        assertThat(addedNote.userId).isEqualTo(userId)
        assertThat(addedNote.noteText).isEqualTo(noteText)
        assertThat(notesBefore).isEmpty()
        val notesAfter = notesRepository.getAllNotesForUser(userId)
        assertThat(notesAfter.size).isEqualTo(1)
        assertThat(notesAfter[0].userId).isEqualTo(userId)
        assertThat(notesAfter[0].noteText).isEqualTo(noteText)
    }

    @Test
    fun `get all notes when no notes exist`() {
        val notes = notesService.getAllNotesForUser("userId")

        assertThat(notes).isEmpty()
    }

    @Test
    fun `get all notes when notes exist`() {
        val userId = "userId"
        val noteText = "noteText"
        notesRepository.add(NoteEntity(userId, "noteId", noteText, 1))

        val notes = notesService.getAllNotesForUser(userId)

        assertThat(notes.size).isEqualTo(1)
        assertThat(notes[0].userId).isEqualTo(userId)
        assertThat(notes[0].noteText).isEqualTo(noteText)
    }
}