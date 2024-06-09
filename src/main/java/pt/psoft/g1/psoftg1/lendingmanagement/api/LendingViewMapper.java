package pt.psoft.g1.psoftg1.lendingmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.*;

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
    @Mapping(target = "_links.self", source = ".", qualifiedByName = "self")
    @Mapping(target = "_links.book", source = ".", qualifiedByName = "book")
    @Mapping(target = "_links.reader", source = ".", qualifiedByName = "reader")
    public abstract LendingView toLendingView(Lending lending);

    public abstract List<LendingView> toLendingView(List<Lending> lendings);

    public abstract LendingsAverageDurationView toLendingsAverageDurationView(Double lendingsAverageDuration);

    @Mapping(target = "self", source = ".", qualifiedByName = "self")
    @Mapping(target = "book", source = ".", qualifiedByName = "book")
    @Mapping(target = "reader", source = ".", qualifiedByName = "reader")
    public abstract LendingLinksView toLendingLinksView(Lending lending);

    @Named(value = "self")
    protected Map<String, String> mapSelfLink(Lending lending){
        Map<String, String> lendingLink = new HashMap<>();
        String lendingUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/lendings/")
                .path(lending.getLendingNumber())
                .toUriString();
        lendingLink.put("href", lendingUri);
        return lendingLink;
    }

    @Named(value = "book")
    protected Map<String, String> mapBookLink(Lending lending){
        Map<String, String> bookLink = new HashMap<>();
        String bookUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/books/")
                .path(lending.getBook().getIsbn())
                .toUriString();
        bookLink.put("href", bookUri);
        return bookLink;
    }

    @Named(value = "reader")
    protected Map<String, String> mapReaderLink(Lending lending){
        Map<String, String> readerLink = new HashMap<>();
        String readerUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/readers/")
                .path(lending.getReaderDetails().getReaderNumber())
                .toUriString();
        readerLink.put("href", readerUri);
        return readerLink;
    }
}
