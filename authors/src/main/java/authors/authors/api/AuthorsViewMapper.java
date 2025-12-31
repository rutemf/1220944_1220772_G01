package authors.authors.api;

import authors.authors.model.Author;
import authors.shared.api.MapperInterface;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class  AuthorsViewMapper extends MapperInterface {

    @Mapping(target = "name", expression = "java(author.getName().toString())")
    @Mapping(target = "_links", expression = "java(mapLinks(author))")
    public abstract AuthorsView toAuthorsView(Author author);

    public abstract List<AuthorsView> toAuthorsView(List<Author> authors);

    @Named("mapAuthorLinks")
    public Map<String, Object> mapLinks(final Author author) {

        String authorUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/authors/")
                .path(String.valueOf(author.getAuthorNumber()))
                .toUriString();

        Map<String, Object> links = new HashMap<>();
        links.put("self", authorUri);
        links.put("photo", generatePhotoUrl(author));

        return links;
    }

    protected String generatePhotoUrl(Author author) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/authors/{id}/photo")
                .buildAndExpand(author.getAuthorNumber())
                .toUri()
                .toString();
    }
}
