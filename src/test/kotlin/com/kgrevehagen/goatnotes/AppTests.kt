package com.kgrevehagen.goatnotes

import assertk.assertThat
import assertk.assertions.isNotNull
import com.kgrevehagen.goatnotes.notes.NotesController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active=test"])
internal class AppTests @Autowired constructor(private val notesController: NotesController) {

	@Test
	fun contextLoads() {
		assertThat(notesController).isNotNull()
	}

}
