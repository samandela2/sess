package com.example.sess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import com.example.sess.models.Task;
import com.example.sess.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.BDDMockito.given;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@SuppressWarnings("null")
class SessTaskTests {

	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	

	public SessTaskTests(){
		objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
	}
	

	@Test
	void contextLoads() {
	}

	// Read
	@Test
	void shouldReturnATaskWhenDataIsSaved() throws Exception {

		mockMvc.perform(get("/tasks/99").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(99))
				.andExpect(jsonPath("$.startTime").value("2024-01-01 10:00:00"));

		mockMvc.perform(get("/tasks/1199").with(user("john").roles("USER")))
				.andExpect(status().isNotFound());

				//admin
		mockMvc.perform(get("/tasks/admin/99").with(user("kyle").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(99))
				.andExpect(jsonPath("$.startTime").value("2024-01-01 10:00:00"));
		
	}

	@Test
	void shouldReturnAllTasksWhenListIsRequested() throws Exception {
		mockMvc.perform(get("/tasks").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$..id").value(containsInAnyOrder(99, 100)))
				.andExpect(jsonPath("$..startTime").value(containsInAnyOrder("2024-01-01 10:00:00", "2024-02-01 15:00:00")));
			//admin
			mockMvc.perform(get("/tasks/admin").with(user("kyle").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(4))
				.andExpect(jsonPath("$..id").value(containsInAnyOrder(99, 100, 102,101)))
				.andExpect(jsonPath("$..startTime").value(containsInAnyOrder("2024-01-01 10:00:00", "2024-02-01 15:00:00", "2024-04-01 19:00:00","2024-03-01 10:00:00")));
			}	

	@Test
	void shouldNotReturnTaskWithUnknownId() throws Exception {
		mockMvc.perform(get("/tasks/10000").with(user("john").roles("USER")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""));
	}

	@Test
	void shouldReturnPageOfTasks() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)));
	}

	@Test
	void ShouldReturnPageOfTasksDesc() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1&sort=startTime,desc").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)))
				.andExpect(jsonPath("$[0].startTime").value("2024-02-01 15:00:00"));
	}

	@Test
	void ShouldReturnPageOfTasksASC() throws Exception {
		mockMvc.perform(get("/tasks?page=0&size=1&sort=startTime,asc").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(1)))
				.andExpect(jsonPath("$[0].startTime").value("2024-01-01 10:00:00"));
	}

	@Test
	void shouldReturnASortedPageOfTaskWithNoParametersAndUseDefaultValues() throws Exception {
		mockMvc.perform(get("/tasks").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasSize(2)))
				.andExpect(jsonPath("$..startTime").value(containsInAnyOrder("2024-01-01 10:00:00", "2024-02-01 15:00:00")));
	}

	

	// create
	@Test
	@Transactional
	void shouldCreateANewTask() throws Exception {
		Task newTask = new Task(null, "01/02/2024 13:00:00",
                "01/02/2024 14:00:00", 1L, 51L,
                "3333 hell st, San Francisco, CA, 94444", "appointment", "happy dayyyyy");
		String newTaskJson = objectMapper.writeValueAsString(newTask);

		MvcResult result = mockMvc.perform(post("/tasks")
				.with(user("kyle").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTaskJson))
				.andExpect(status().isCreated())
				.andReturn();

		String location = result.getResponse().getHeader("Location");

		mockMvc.perform(get(location).with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.startTime").value("2024-01-02 13:00:00"));
	}

	@Test
	@Transactional
	void shouldNotCreateADuplicateTask() throws Exception {
		Task newTask = new Task(null, "01/01/2024 10:00:00",
                "01/01/2024 11:00:00", 1L, 50L,
                "3333 hell st, San Francisco, CA, 94444", "appointment", "happy day");
				
				String newTaskJson = objectMapper.writeValueAsString(newTask);

				MvcResult result = mockMvc.perform(post("/tasks")
						.with(user("kyle").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(newTaskJson))
						.andExpect(status().isConflict())
						.andReturn();
						
	}


	// update
	@Test
	@Transactional
	void shouldUpdateExistingTask() throws Exception {
		Task taskToUpdate = new Task(null, "01/01/2024 10:30:00","01/01/2024 11:00:00",
                 1L, 51L, "33 hl st, San Francisco, CA, 94444",
                "appointment", "happy day");
		String taskJson = objectMapper.writeValueAsString(taskToUpdate);

		mockMvc.perform(put("/tasks/99")
				.with(user("john").roles("USER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/tasks/99").with(user("john").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.startTime").value("2024-01-01 10:30:00"));
	}

	@Test
	@Transactional
	void shouldNotUpdateNonExistingTask() throws Exception {
		Task taskToUpdate = new Task(null, "01/01/2024 10:30:00","01/01/2024 11:00:00",
		31L, 51L, "33 hl st, San Francisco, CA, 94444", "appointment", "happy day");
		String taskJson = objectMapper.writeValueAsString(taskToUpdate);

		mockMvc.perform(put("/tasks/999999")
				.with(user("john").roles("USER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))
				.andExpect(status().isNotFound());
	}

	// delete
	@Test
	@Transactional
	void shouldDeleteAnExistingTask() throws Exception {
		mockMvc.perform(delete("/tasks/99").with(user("john").roles("USER")))
				.andExpect(status().isNoContent());
	}

	@Test
	@Transactional
	void shouldNotDeleteANonExistingTask() throws Exception {
		mockMvc.perform(delete("/tasks/99999").with(user("john").roles("USER")))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	void shouldNotAllowDeletionOfTasksTheyDoNotOwn() throws Exception {
		mockMvc.perform(delete("/tasks/101").with(user("john").roles("USER")))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/tasks/101").with(user("jane").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(101))
				.andExpect(jsonPath("$.startTime").value("2024-03-01 10:00:00"));
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
	void shouldRejectUsersWhoIsNotTaskOwnerOrAdmin() throws Exception {
		mockMvc.perform(get("/tasks/99").with(user("jane").roles("USER")))
				.andExpect(status().isNotFound());
		mockMvc.perform(get("/tasks/admin/99").with(user("jane").roles("USER")))
				.andExpect(status().isForbidden());
		mockMvc.perform(get("/tasks/admin/99").with(user("kyle").roles("ADMIN")))
				.andExpect(status().isOk());
	}


	@Test
	void shouldNotAllowAccessToTaskTheyDoNotOwn() throws Exception {
		mockMvc.perform(get("/tasks/101").with(user("jane").roles("USER")))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional 
	void shouldNotAllowUserToCreateTask() throws Exception{
		Task newTask = new Task(null, "01/02/2024 13:00:00",
                "01/02/2024 14:00:00", 30L, 51L,
                "3333 hell st, San Francisco, CA, 94444", "appointment", "happy dayyyyy");
		String newTaskJson = objectMapper.writeValueAsString(newTask);

		MvcResult result = mockMvc.perform(post("/tasks")
				.with(user("john").roles("USER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTaskJson))
				.andExpect(status().isForbidden())
				.andReturn();

		
	}

/**
 * // Create an encoder with strength 16
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
 */

	}

