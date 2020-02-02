package pl.muklejski.searchengine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Document {

	@JsonIgnore
	private long id;

	@NotNull(message = "Document cannot be empty.")
	@JsonProperty("document")
	private String origin;

	@JsonIgnore
	private int numberOfTokens;

	@JsonCreator
	public Document(String origin) {
		this.origin = origin;
	}
}
