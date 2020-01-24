package pl.muklejski.searchengine.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.muklejski.searchengine.model.Document;
import pl.muklejski.searchengine.model.tokenizer.Tokenizer;

@Service
@RequiredArgsConstructor
public class InvertedIndexImpl implements InvertedIndex {

	private final Map<String, Map<Document, Long>> invertedIndex = new HashMap<>();
	private final Tokenizer tokenizerNonWordSpliter;

	@Getter
	private AtomicLong numberOfAllDocuments = new AtomicLong(0L);


	@Override
	public void addDocuments(List<Document> documents) {
		for (Document document : documents) {
			List<String> tokens = tokenizerNonWordSpliter.tokenize(document);
			document.setNumberOfTokens(tokens.size());
			addTokensToIndex(tokens, document);
			numberOfAllDocuments.getAndIncrement();
		}
	}

	private void addTokensToIndex(List<String> tokens, Document document) {
		for (String token : tokens) {
			Map<Document, Long> documentMap = new HashMap<>();
			documentMap.put(document, 1L);
			invertedIndex.merge(token,
					    documentMap,
					    (oldDocumentsMap, newDocumentMap) -> {
						    newDocumentMap.forEach((documentKey, counter) -> oldDocumentsMap.merge(documentKey, counter, Long::sum));
						    return oldDocumentsMap;
					    });
		}
	}

	@Override
	public Map<Document, Long> findDocuments(String token) {
		return Optional.ofNullable(invertedIndex.get(token)).orElse(Collections.emptyMap());
	}

}
