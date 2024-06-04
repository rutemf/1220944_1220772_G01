package pt.psoft.g1.psoftg1.lendingmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Brief guides:
 * <a href="https://www.baeldung.com/mapstruct">https://www.baeldung.com/mapstruct</a>
 * <p>
 * <a href="https://medium.com/@susantamon/mapstruct-a-comprehensive-guide-in-spring-boot-context-1e7202da033e">https://medium.com/@susantamon/mapstruct-a-comprehensive-guide-in-spring-boot-context-1e7202da033e</a>
 * */
@Mapper(componentModel = "spring")
public abstract class LendingViewMapper extends MapperInterface {

    @Mapping(target = "lendingNumber", source = "lendingNumber")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "fineValueInCents", expression = "java(lending.getFineValueInCents().orElse(null))")
    @Mapping(target = "links", expression = "java(mapLinks(lending))")
    public abstract LendingView toLendingView(Lending lending);

    public abstract List<LendingView> toLendingView(List<Lending> lendings);

    public Map<String, String> mapLinks(final Lending lending){
        String lendingUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/lendings/")
                .path(lending.getLendingNumber())
                .toUriString();

        String bookUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/books/")
                .path(lending.getBook().getIsbn())
                .toUriString();

        String readerUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/readers/")
                .path(lending.getReaderDetails().getReaderNumber())
                .toUriString();

        Map<String, String> links = new HashMap<>();

        links.put("self", lendingUri);
        links.put("book", bookUri);
        links.put("reader", readerUri);

        return links;
    }

}
