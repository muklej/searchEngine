package pl.muklejski.searchengine.service;

import java.util.List;
import pl.muklejski.searchengine.model.Document;

public interface SearchEngineService {

	void addDocuments(List<Document> documents);
	List<Document> findDocuments(String token);

}
