package br.com.alura.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {

	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";

	@Autowired
	private MockMvc mockMvc;

	private URI authUri;

	@BeforeAll
	void setUp() {
		authUri = UriComponentsBuilder.fromPath("/user").build().toUri();
	}

	@Test
	@DisplayName("tenta autenticar com dados inválidos")
	void performFailedAuthTest() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode authData = mapper.createObjectNode();
		authData.put(USERNAME, "qux");
		authData.put(PASSWORD, "987654");
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.post(authUri)
					.content(authData.toString())
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is4xxClientError());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("tenta autenticar com credenciais existentes e válidas")
	void performSucessfulAuthTest() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode authData = mapper.createObjectNode();
		authData.put(USERNAME, "foo");
		authData.put(PASSWORD, "123456");
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.post(authUri)
					.content(authData.toString())
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is2xxSuccessful());
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}
	}
}