package pl.muklejski.searchengine.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import pl.muklejski.searchengine.model.Document;

public interface InvertedIndex {

	void addDocuments(List<Document> documents);
	Map<Document, Long> findDocuments(String token);
	AtomicLong getNumberOfAllDocuments();

}
