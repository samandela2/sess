// package com.example.sess;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;

// import jakarta.transaction.Transactional;
// import com.example.sess.models.Task;
// import com.example.sess.models.User;
// import com.example.sess.services.UserService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.containsInAnyOrder;
// import static org.hamcrest.Matchers.hasSize;
// import org.springframework.test.web.servlet.MvcResult;
// import static org.mockito.BDDMockito.given;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.test.context.support.WithMockUser;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// @AutoConfigureMockMvc

// class SessUserTests {

// 	@Autowired
// 	private MockMvc mockMvc;
// 	private ObjectMapper objectMapper = new ObjectMapper();

//     @Autowired
// 	private UserService userService;


// 	public SessUserTests(){
// 		objectMapper = new ObjectMapper();
//         objectMapper.registerModule(new JavaTimeModule());
// 	}
	

	
//     @Test
//     @Transactional
//     void shouldCreateANewUser(){
//         User nUser = userService.createUser("jay", "pwd123", "1@gmail.com","User");
//         assertThat(nUser).isNotNull();
//     }


// }

//yrsyrstrestres