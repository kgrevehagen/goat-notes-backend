package com.kgrevehagen.goatnotes

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active=test"])
class AppTests {

	@Test
	fun contextLoads() {
	}

}
