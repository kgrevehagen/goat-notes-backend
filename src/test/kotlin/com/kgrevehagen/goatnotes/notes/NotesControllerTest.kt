package com.kgrevehagen.goatnotes.notes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kgrevehagen.goatnotes.auth.SecurityConfiguration
import com.kgrevehagen.goatnotes.auth.WithMockJwt
import com.kgrevehagen.goatnotes.notes.model.CreateNoteRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.json.JsonCompareMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@Import(SecurityConfiguration::class)
@WebMvcTest(NotesController::class, properties = ["spring.profiles.active=test"])
internal class NotesControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun notesService(): NotesService = FakeNotesService()
    }

    @Test
    fun `create note should return 401 when not authenticated`() {
        mockMvc.post("/notes")
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    @WithMockJwt
    fun `create note should create a note when authenticated`() {
        val noteJson = jacksonObjectMapper().writeValueAsString(CreateNoteRequest("Note Text"))

        val expectedJson = """
        {
            "userId": "user-id-123",
            "noteId": "1",
            "noteText": "Note Text",
            "createdAt": 1
        }
        """.trimIndent()

        mockMvc.post("/notes") {
            contentType = MediaType.APPLICATION_JSON
            content = noteJson
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(expectedJson, JsonCompareMode.STRICT) }
            }
    }

    @Test
    @WithMockJwt
    fun `create note with wrong body should not create a note`() {
        mockMvc.post("/notes") {
            contentType = MediaType.APPLICATION_JSON
            content = "wrong json"
        }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    fun `get notes should return 401 when not authenticated`() {
        mockMvc.get("/notes")
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    @WithMockJwt("user-id-empty")
    fun `get notes should return empty notes when authenticated`() {
        val expectedJson = "[]"

        mockMvc.get("/notes")
            .andExpect {
                status { isOk() }
                jsonPath("$.size()") { value(0) }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(expectedJson, JsonCompareMode.STRICT) }
            }
    }

    @Test
    @WithMockJwt
    fun `get notes should return notes when authenticated`() {
        val expectedJson = """
        [
            {
                "userId": "user-id-123",
                "noteId": "1",
                "noteText": "note text",
                "createdAt": 1
            }
        ]
        """.trimIndent()

        mockMvc.get("/notes")
            .andExpect {
                status { isOk() }
                jsonPath("$.size()") { value(1) }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(expectedJson, JsonCompareMode.STRICT) }
            }
    }
}
