package pl.muklejski.searchengine.service;

import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.muklejski.searchengine.model.Document;
import pl.muklejski.searchengine.model.tokenizer.Tokenizer;
import pl.muklejski.searchengine.model.tokenizer.TokenizerNonWordSpliter;

public class TokenizerTest {

	@Test
	public void tokenizeTest() {
		//given
		Tokenizer tokenizer = new TokenizerNonWordSpliter();
		Document document = new Document("Testowy tekst  co to będzie,@ # \t \n  %^&*(;;: \r \f  \"'[]{}-= , nie wiem dlaczego ? http://www.wp.pl");

		//when
		List<String> tokens = tokenizer.tokenize(document);

		//then
		Assert.assertEquals(12, tokens.size());
	}
}