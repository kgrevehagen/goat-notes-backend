package com.kgrevehagen.goatnotes.notes

import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import com.kgrevehagen.goatnotes.notes.model.NoteDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @DeleteMapping("/{noteId}")
    @PreAuthorize("isAuthenticated()")
    fun deleteNoteForUser(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable noteId: String
    ): ResponseEntity<Map<String, String>> {
        val userId = jwt.subject
        return try {
            notesService.deleteNoteForUser(userId, noteId)
            ResponseEntity.noContent().build()
        } catch (_: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Note with id $noteId not found"))
        }
    }
}
