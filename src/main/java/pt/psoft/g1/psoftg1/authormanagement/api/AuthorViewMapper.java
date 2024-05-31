package pt.psoft.g1.psoftg1.authormanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorCountView;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper extends MapperInterface {

    @Mapping(target = "authorNumber", source = "authorNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "bio", source = "bio")
    public abstract AuthorView toAuthorView(Author author);

    public abstract List<AuthorView> toAuthorView(List<Author> authors);
    @Mapping(target = "authorView", source = "author")
    public abstract AuthorCountView toAuthorCountView(AuthorCountDTO authorCountView);

    public abstract List<AuthorCountView> toAuthorCountView(List<AuthorCountView> authorCountView);

    public abstract List<AuthorCountView> toAuthorCountViewList(List<AuthorCountDTO> authorCountDTOList);

    public String[] map(List<Author> authorList) {
        String[] authorNames = new String[authorList.size()];
        for(int i = 0; i < authorList.size(); i++) {
            authorNames[i] = authorList.get(i).getName().toString();
        }

        return authorNames;
    }



}
