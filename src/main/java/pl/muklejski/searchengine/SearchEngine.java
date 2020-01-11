package pl.muklejski.searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class SearchEngine {

	public static void main(String[] args) {
		SpringApplication.run(SearchEngine.class, args);
	}

}
