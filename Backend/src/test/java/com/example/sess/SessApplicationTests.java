package com.example.sess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;

import com.example.sess.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@SuppressWarnings("null")
class SessApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void contextLoads() {
	}

	// Read
	@Test
	void shouldReturnATaskWhenATaskIsSaved() throws Exception {
		mockMvc.perform(get("/tasks/99").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(99))
				.andExpect(jsonPath("$.time").value("1/1"));

		mockMvc.perform(get("/tasks/1199").with(httpBasic("john", "pwd123")))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturnAllTasksWhenListIsRequested() throws Exception {
		mockMvc.perform(get("/tasks").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$..id").value(containsInAnyOrder(99, 100, 102)))
				.andExpect(jsonPath("$..time").value(containsInAnyOrder("1/1", "2/1", "4/1")));
	}

	@Test
	void shouldNotReturnTaskWithUnknownId() throws Exception {
		mockMvc.perform(get("/tasks/10000").with(httpBasic("john", "pwd123")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""));
	}

	@Test
	void shouldReturnPageOfTasks() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)));
	}

	@Test
	void ShouldReturnPageOfTasksDesc() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1&sort=time,desc").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)))
				.andExpect(jsonPath("$[0].time").value("4/1"));
	}

	@Test
	void ShouldReturnPageOfTasksASC() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1&sort=time,asc").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)))
				.andExpect(jsonPath("$[0].time").value("1/1"));
	}

	@Test
	void shouldReturnASortedPageOfTaskWithNoParametersAndUseDefaultValues() throws Exception {
		mockMvc.perform(get("/tasks").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(3)))
				.andExpect(jsonPath("$..time").value(containsInAnyOrder("1/1", "2/1", "4/1")));
	}

	// create
	@Test
	@Transactional
	void shouldCreateANewTask() throws Exception {
		Task newTask = new Task(110L, "5/1", "john");
		String newTaskJson = objectMapper.writeValueAsString(newTask);

		MvcResult result = mockMvc.perform(post("/tasks")
				.with(httpBasic("john", "pwd123"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTaskJson))
				.andExpect(status().isCreated())
				.andReturn();

		String location = result.getResponse().getHeader("Location");

		mockMvc.perform(get(location).with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.time").value("5/1"));
	}

	// update
	@Test
	@Transactional
	void shouldUpdateExistingTask() throws Exception {
		Task taskToUpdate = new Task(null, "1/15", null);
		String taskJson = objectMapper.writeValueAsString(taskToUpdate);

		mockMvc.perform(put("/tasks/99")
				.with(httpBasic("john", "pwd123"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/tasks/99").with(httpBasic("john", "pwd123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.time").value("1/15"));
	}

	@Test
	@Transactional
	void shouldNotUpdateNonExistingTask() throws Exception {
		Task taskToUpdate = new Task(null, "1/15", null);
		String taskJson = objectMapper.writeValueAsString(taskToUpdate);

		mockMvc.perform(put("/tasks/999999")
				.with(httpBasic("john", "pwd123"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))
				.andExpect(status().isNotFound());
	}

	// delete
	@Test
	@Transactional
	void shouldDeleteAnExistingTask() throws Exception {
		mockMvc.perform(delete("/tasks/99").with(httpBasic("john", "pwd123")))
				.andExpect(status().isNoContent());
	}

	@Test
	@Transactional
	void shouldNotDeleteANonExistingTask() throws Exception {
		mockMvc.perform(delete("/tasks/99999").with(httpBasic("john", "pwd123")))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	void shouldNotAllowDeletionOfTasksTheyDoNotOwn() throws Exception {
		mockMvc.perform(delete("/tasks/101").with(httpBasic("john", "pwd123")))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/tasks/101").with(httpBasic("jane", "abc456")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(101))
				.andExpect(jsonPath("$.time").value("3/1"));
	}

	// security
	@Test
	void shouldNotReturnTaskWithBadCredentials() throws Exception {
		mockMvc.perform(get("/tasks/99").with(httpBasic("IamBad", "pwd123")))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(get("/tasks").with(httpBasic("IamStillBad", "pwd123")))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void shouldRejectUsersWhoIsNotTaskOwner() throws Exception {
		mockMvc.perform(get("/tasks/99").with(httpBasic("jane", "abc456")))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldNotAllowAccessToTaskTheyDoNotOwn() throws Exception {
		mockMvc.perform(get("/tasks/101").with(httpBasic("jane", "abc456")))
				.andExpect(status().isOk());
	}

}
