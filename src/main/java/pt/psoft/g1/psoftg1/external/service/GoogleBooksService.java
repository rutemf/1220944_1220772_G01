package pt.psoft.g1.psoftg1.external.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Profile("google")
public class GoogleBooksService implements IsbnService {
    private final WebClient webClient;
    public GoogleBooksService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes").build();
    }
    @Override
    public String fetchIsbnsByTitle(String title) {
        try {
            Mono<Map> responseMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/volumes")
                            .queryParam("q", "intitle:" + title)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class);

            Map<String, Object> response = responseMono.block();

            if (response != null && response.containsKey("items")) {
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                return getFirstIsbn(items);
            }
            return null;

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error fetching data from Google Books API: " + e.getMessage(), e);
        }
    }

    private String getFirstIsbn(List<Map<String, Object>> items) {
        for (Map<String, Object> item : items) {
            Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");
            if (volumeInfo != null && volumeInfo.containsKey("industryIdentifiers")) {
                List<Map<String, String>> identifiers =
                        (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
                for (Map<String, String> id : identifiers) {
                    String type = id.get("type");
                    if ("ISBN_13".equals(type) || "ISBN_10".equals(type)) {
                        return id.get("identifier");
                    }
                }
            }
        }
        return null;
    }
}
