package com.abhiesa.knote;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = KNoteApplication.class)
@AutoConfigureMockMvc
class KNoteApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private KNoteService service;

	private static final List<Note> MOCK_NOTE_LIST = Arrays.asList(new Note("1", "Note 1"),
			new Note("2", "Note 2"), new Note("3", "Note 3"),  new Note("4", "Note 4"));


	@Test
	void testIndex() throws Exception {
		Mockito.when(service.getAllNotes()).thenReturn(MOCK_NOTE_LIST);
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("notes",MOCK_NOTE_LIST ));
	}

	@Test
	void testSaveNotes1() throws Exception {
		Mockito.when(service.getAllNotes()).thenReturn(MOCK_NOTE_LIST);
		final MockMultipartFile file = new MockMultipartFile("image", "hello.txt",
				MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/note").file(file)
				.param("description", "hello")
		)
				.andExpect(MockMvcResultMatchers.status().isOk());
				//.andExpect(MockMvcResultMatchers.model().attribute("notes",MOCK_NOTE_LIST ));
	}

	@Test
	void testSaveNotes2() throws Exception {
		Mockito.when(service.getAllNotes()).thenReturn(MOCK_NOTE_LIST);
		Mockito.doNothing().when(service).saveNote("hello");
		final MockMultipartFile file = new MockMultipartFile("image", "hello.txt",
				MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

		mockMvc.perform(MockMvcRequestBuilders.multipart("/note").file(file)
				.param("description", "hello")
				.param("publish", "Publish")
		).andExpect(MockMvcResultMatchers.status().isFound());
	}

	@Test
	void testSaveNotes3() throws Exception {
		Mockito.when(service.getAllNotes()).thenReturn(MOCK_NOTE_LIST);
		Mockito.when(service.uploadImage(Mockito.any(), Mockito.anyString())).thenReturn("bye");
		Mockito.doNothing().when(service).saveNote("hello");
		final MockMultipartFile file = new MockMultipartFile("image", "hello.txt",
				MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/note").file(file)
				.param("description", "hello")
				.param("upload", "Upload")
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
