package pl.muklejski.searchengine.controler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.muklejski.searchengine.model.Document;
import pl.muklejski.searchengine.model.constants.SearchApi;
import pl.muklejski.searchengine.service.SearchEngineService;
import pl.muklejski.searchengine.utils.SequenceGenerator;

@Validated
@RestController
@RequestMapping(value = SearchApi.SEARCH_ENGINE_ENDPOINT)
@AllArgsConstructor
public class SearchController {

	private SequenceGenerator sequenceGenerator;
	private SearchEngineService searchEngineService;

	@PostMapping(value = SearchApi.ADD_DOCUMENTS_ENDPOINT)
	@ResponseBody
	public String addDocuments(@RequestBody @NotEmpty(message = "Input documents list cannot be empty.") List<@Valid Document> documents) {
		if (documents.stream().anyMatch(Objects::isNull)) {
			throw new ConstraintViolationException("Document in list cannot be null", Collections.emptySet());
		}
		documents.forEach(document -> document.setId(sequenceGenerator.getNext()));
		searchEngineService.addDocuments(documents);
		return "Words added";
	}

	@GetMapping(value = SearchApi.SEARCH_ENDPOINT)
	@ResponseBody
	public List<Document> findDocuments(@RequestParam @NotNull @Valid String token) {
		return searchEngineService.findDocuments(token);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
		String errorMessage;
		if (!violations.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
			errorMessage = builder.toString();
		} else {
			errorMessage = "ConstraintViolationException occured.";
		}
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

}