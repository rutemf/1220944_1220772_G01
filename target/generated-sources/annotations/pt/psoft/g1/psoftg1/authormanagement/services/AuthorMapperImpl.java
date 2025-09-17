package pt.psoft.g1.psoftg1.authormanagement.services;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T22:03:47+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl extends AuthorMapper {

    @Override
    public Author create(CreateAuthorRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        String bio = null;
        String photoURI = null;

        name = map( request.getName() );
        bio = map( request.getBio() );
        photoURI = map( request.getPhotoURI() );

        Author author = new Author( name, bio, photoURI );

        author.setPhoto( map( request.getPhotoURI() ) );

        return author;
    }

    @Override
    public void update(UpdateAuthorRequest request, Author author) {
        if ( request == null ) {
            return;
        }

        author.setPhoto( map( request.getPhoto() ) );
        author.setName( map( request.getName() ) );
        author.setBio( map( request.getBio() ) );
    }
}
