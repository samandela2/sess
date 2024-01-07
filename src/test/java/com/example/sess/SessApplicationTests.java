package com.example.sess;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.jayway.jsonpath.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SessApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void shouldReturnATaskWhenATaskIsSaved() {
		ResponseEntity<String> response = testRestTemplate
				// .withBasicAuth("John", "pwd123")
				.getForEntity("/tasks/199", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		String time = documentContext.read("$.time");
		assertThat(time).isEqualTo("1/1");


		// String responseBody = response.getBody();
		// System.out.println(responseBody);
		

	}

}
