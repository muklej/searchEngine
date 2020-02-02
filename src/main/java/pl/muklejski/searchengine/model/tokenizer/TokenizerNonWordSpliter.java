package pl.muklejski.searchengine.model.tokenizer;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.muklejski.searchengine.model.Document;

@Component
public class TokenizerNonWordSpliter implements Tokenizer{

	@Override
	public List<String> tokenize(Document document) {
		return Arrays.asList(
			document
				.getOrigin()
				.toLowerCase()
				.split("[`'!@#$%^&* ,.?/()_=+|{}\\[\\]:;\"<>\\-\n\f\r\t]+")
		);
	}

}
