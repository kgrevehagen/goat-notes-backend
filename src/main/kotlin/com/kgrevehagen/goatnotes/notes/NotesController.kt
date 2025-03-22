package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import com.kgrevehagen.goatnotes.notes.model.NoteDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
internal class NotesController(private val notesService: NotesService) {

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createNote(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody noteRequest: CreateNoteRequest
    ): ResponseEntity<NoteDto> {
        val userId = jwt.subject
        val note = notesService.add(userId, noteRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(note)
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getAllNotesForUser(@AuthenticationPrincipal jwt: Jwt): List<NoteDto> {
        val userId = jwt.subject
        return notesService.getAllNotesForUser(userId)
    }
}
