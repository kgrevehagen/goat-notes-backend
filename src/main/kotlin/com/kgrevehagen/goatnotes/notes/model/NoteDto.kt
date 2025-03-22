package com.kgrevehagen.goatnotes.notes.model

data class NoteDto(
    var userId: String,
    var noteId: String,
    var noteText: String,
    var createdAt: Long,
)
