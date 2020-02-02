package pl.muklejski.searchengine.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.muklejski.searchengine.model.Document;
import pl.muklejski.searchengine.service.sorter.Sorter;

@Service
@RequiredArgsConstructor
public class SearchEngineServiceImpl implements SearchEngineService {

	private final InvertedIndex invertedIndexImpl;
	private final Sorter tfIdfSorter;

	@Override
	public void addDocuments(List<Document> documents) {
		invertedIndexImpl.addDocuments(documents);
	}

	@Override
	public List<Document> findDocuments(String token) {
		Map<Document, Long> documents = invertedIndexImpl.findDocuments(token);
		long numberOfAllDocuments = invertedIndexImpl.getNumberOfAllDocuments().get();
		return tfIdfSorter.sort(documents, numberOfAllDocuments);
	}


}
