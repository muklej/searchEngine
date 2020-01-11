package pl.muklejski.searchengine.service.sorter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.muklejski.searchengine.model.Document;

@Component
@RequiredArgsConstructor
public class TfIdfSorter implements Sorter{

	@Override
	public List<Document> sort(Map<Document, Long> documents, long numberOfAllDocuments) {
		Map<Document, Double> tfidfMap = calculateTfIdf(documents, numberOfAllDocuments);
		return documents
			.keySet()
			.stream()
			.sorted(Comparator.comparing(tfidfMap::get).reversed())
			.collect(Collectors.toList());
	}

	private Map<Document, Double> calculateTfIdf(Map<Document, Long> documents, long numberOfAllDocuments) {
		Map<Document, Double> tfidfMap = new HashMap<>();
		int numberOfDocumentsContainingToken = documents.size();
		double idf = Math.log10(numberOfAllDocuments * 1D / numberOfDocumentsContainingToken);
		documents.forEach((document, tokenCounter) -> {
			double tf = tokenCounter * 1D / document.getNumberOfTokens();
			double tfidf = tf * idf;
			tfidfMap.put(document, tfidf);
		});
		return tfidfMap;
	}
}
