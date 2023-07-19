package ru.netology.springbootconditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootConditionalApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	private final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp")
			.withExposedPorts(8080);
	private final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);
	@BeforeEach
	public void setUp() {
		myAppFirst.start();
		myAppSecond.start();
	}
	@Test
	void contextLoadsFirst() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080) + "/profile", String.class);
		System.out.println(forEntity.getBody());
		Assertions.assertEquals("Current profile is dev", forEntity.getBody());
	}

	@Test
	void contextLoadsSecond() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myAppSecond.getMappedPort(8081) + "/profile", String.class);
		System.out.println(forEntity.getBody());
		Assertions.assertEquals("Current profile is production", forEntity.getBody());
	}
}