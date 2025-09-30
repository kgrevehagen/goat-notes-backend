package com.kgrevehagen.goatnotes.notes.ui

import com.kgrevehagen.goatnotes.notes.NotesService
import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/ui/notes")
@PreAuthorize("isAuthenticated()")
internal class NotesUiController(private val notesService: NotesService) {

    @GetMapping
    fun showNotes(@AuthenticationPrincipal jwt: OAuth2User, model: Model): String {
        val userId = jwt.attributes["sub"] as String
        val name = jwt.attributes["preferred_username"] as? String ?: userId
        val notes = notesService.getAllNotesForUser(userId)
        model.addAttribute("notes", notes)
        model.addAttribute("name", name)
        return "notes"
    }

    @PostMapping
    fun addNote(
        @AuthenticationPrincipal jwt: OAuth2User,
        @RequestParam action: String,
        @RequestParam noteText: String?,
        @RequestParam noteId: String?,
    ): String {
        val userId = jwt.attributes["sub"] as String
        when (action) {
            "create" -> noteText?.let { notesService.add(userId, CreateNoteRequest(it)) }
            "delete" -> noteId?.let { notesService.deleteNoteForUser(userId, it) }
        }

        return "redirect:/ui/notes"
    }
}
