package pl.muklejski.searchengine.service.sorter;

import java.util.List;
import java.util.Map;
import pl.muklejski.searchengine.model.Document;

public interface Sorter {

	List<Document> sort(Map<Document, Long> documents, long numberOfAllDocuments);
}
