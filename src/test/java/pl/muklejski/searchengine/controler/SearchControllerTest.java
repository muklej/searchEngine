package pl.muklejski.searchengine.controler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;
import pl.muklejski.searchengine.model.Document;
import static pl.muklejski.searchengine.model.constants.SearchApi.ADD_DOCUMENTS_ENDPOINT;
import static pl.muklejski.searchengine.model.constants.SearchApi.SEARCH_ENDPOINT;
import static pl.muklejski.searchengine.model.constants.SearchApi.SEARCH_ENGINE_ENDPOINT;


@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext wac;


	@Test
	public void givenWac_whenServletContext_thenItProvidesSearchController() {
		//given
		ServletContext servletContext = wac.getServletContext();

		//then
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("searchController"));
	}

	@Test
	public void addDocumentsTest() throws Exception {
		//given
		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document2 = new Document("the lazy brown dog sat in the corner");
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			document3
		);
		String documentsInString = objectMapper.writeValueAsString(documents);

		//when
		mockMvc.perform(post(SEARCH_ENGINE_ENDPOINT + ADD_DOCUMENTS_ENDPOINT)
					.contentType(APPLICATION_JSON)
					.content(documentsInString))
			//then
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Words added"));
	}

	@Test
	public void addDocumentsNullObjectTest() throws Exception {
		//given
		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			null,
			document3
		);
		String documentsInString = objectMapper.writeValueAsString(documents);

		//when
		mockMvc.perform(post(SEARCH_ENGINE_ENDPOINT + ADD_DOCUMENTS_ENDPOINT)
					.contentType(APPLICATION_JSON)
					.content(documentsInString))
			//then
			.andExpect(status().is4xxClientError());
	}

	@Test
	public void addDocumentsNullTextTest() throws Exception {
		//given
		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document2 = new Document(null);
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			document3
		);
		String documentsInString = objectMapper.writeValueAsString(documents);

		//when
		mockMvc.perform(post(SEARCH_ENGINE_ENDPOINT + ADD_DOCUMENTS_ENDPOINT)
					.contentType(APPLICATION_JSON)
					.content(documentsInString))
			//then
			.andExpect(status().is4xxClientError());
	}

	@Test
	public void addDocumentsWithEmptyListTest() throws Exception {
		//when
		mockMvc.perform(post(SEARCH_ENGINE_ENDPOINT + ADD_DOCUMENTS_ENDPOINT)
					.contentType(APPLICATION_JSON)
					.content("[]"))
			//then
			.andExpect(status().is4xxClientError());
	}

	@Test
	public void findDocumentsTest() throws Exception {
		//given
		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document2 = new Document("the lazy brown dog sat in the corner");
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			document3
		);
		String documentsInString = objectMapper.writeValueAsString(documents);

		//when
		mockMvc.perform(post(SEARCH_ENGINE_ENDPOINT + ADD_DOCUMENTS_ENDPOINT)
					.contentType(APPLICATION_JSON)
					.content(documentsInString))
			.andExpect(status().isOk());
		MvcResult result = mockMvc.perform(get(SEARCH_ENGINE_ENDPOINT + SEARCH_ENDPOINT)
							   .contentType(APPLICATION_JSON)
							   .param("token", "brown"))
			//then
			.andExpect(status().isOk())
			.andReturn();
		List<Document> foundDocuments = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Document>>() {});

		//then
		Assert.assertFalse(foundDocuments.isEmpty());
		Assert.assertEquals(2, foundDocuments.size());
		Assert.assertEquals(document1, foundDocuments.get(0));
		Assert.assertEquals(document2, foundDocuments.get(1));
	}
}