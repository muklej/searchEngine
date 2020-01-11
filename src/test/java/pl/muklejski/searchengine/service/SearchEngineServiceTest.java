package pl.muklejski.searchengine.service;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.muklejski.searchengine.model.Document;

@SpringBootTest
public class SearchEngineServiceTest {

	@Autowired
	private SearchEngineService searchEngineService;

	@Test
	public void findDocumentsTest() {
		//given
		Document document1 = new Document("Jesteśmy na wzgórzu i patrzymy na niebo - myślę, że dobrze się bawimy. \nJednak niebo zaczyna pokrywać się warstwą chmur.");
		Document document2 = new Document("Jesteśmy na wzgórzu i chmury zasłaniają nam niebo.");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			new Document("Weszliśmy na wzgórze i dobrze się bawimy.")
		);
		searchEngineService.addDocuments(documents);

		//when
		List<Document> foundDocuments = searchEngineService.findDocuments("niebo");

		//then
		Assert.assertFalse(foundDocuments.isEmpty());
		Assert.assertEquals(2, foundDocuments.size());
		Assert.assertEquals(document2, foundDocuments.get(0));
		Assert.assertEquals(document1, foundDocuments.get(1));
	}

	@Test
	public void exampleTestBrown() {
		//given
		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document2 = new Document("the lazy brown dog sat in the corner");
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			document3
		);
		searchEngineService.addDocuments(documents);

		//when
		List<Document> foundDocuments = searchEngineService.findDocuments("brown");

		//then
		Assert.assertFalse(foundDocuments.isEmpty());
		Assert.assertEquals(2, foundDocuments.size());
		Assert.assertEquals(document1, foundDocuments.get(0));
		Assert.assertEquals(document2, foundDocuments.get(1));
	}

	@Test
	public void exampleTestFox() {
		//given

		Document document1 = new Document("the brown fox jumped over the brown dog");
		Document document2 = new Document("the lazy brown dog sat in the corner");
		Document document3 = new Document("the red fox bit the lazy dog");
		List<Document> documents = Arrays.asList(
			document1,
			document2,
			document3
		);
		searchEngineService.addDocuments(documents);

		//when
		List<Document> foundDocuments = searchEngineService.findDocuments("fox");

		//then
		Assert.assertFalse(foundDocuments.isEmpty());
		Assert.assertEquals(2, foundDocuments.size());
		Assert.assertEquals(document3, foundDocuments.get(0));
		Assert.assertEquals(document1, foundDocuments.get(1));
	}
}