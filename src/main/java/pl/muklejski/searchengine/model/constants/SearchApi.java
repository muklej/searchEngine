package pl.muklejski.searchengine.model.constants;

public class SearchApi {

	private SearchApi() {
		throw new IllegalStateException("Constants class");
	}

	public static final String SEARCH_ENGINE_ENDPOINT = "/v1";
	public static final String ADD_DOCUMENTS_ENDPOINT = "/addDocuments";
	public static final String SEARCH_ENDPOINT = "/search";
}
