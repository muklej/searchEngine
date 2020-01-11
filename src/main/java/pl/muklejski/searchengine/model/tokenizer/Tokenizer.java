package pl.muklejski.searchengine.model.tokenizer;

import java.util.List;
import pl.muklejski.searchengine.model.Document;

public interface Tokenizer {

	List<String> tokenize(Document document);
}
