package com.example.sess;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SessApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void shouldReturnATaskWhenATaskIsSaved() {
		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("john", "pwd123")
				.getForEntity("/tasks/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);
		String time = documentContext.read("$.time");
		assertThat(time).isEqualTo("1/1");
		
		ResponseEntity<String> badresponse = testRestTemplate
				.withBasicAuth("john", "pwd123")
				.getForEntity("/tasks/1199", String.class);

		assertThat(badresponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnAllTasksWhenListIsRequested(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john","pwd123")
			.getForEntity("/tasks", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int taskCount = documentContext.read("$.length()");
		assertThat(taskCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(99,100,102);
		JSONArray times = documentContext.read("$..time");
		assertThat(times).containsExactlyInAnyOrder("1/1","2/1","4/1");
	}

	@Test
	void shouldNotReturnTaskWithUnknownId(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks/10000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);	
		assertThat(response.getBody()).isBlank();
	}


	

	

}
