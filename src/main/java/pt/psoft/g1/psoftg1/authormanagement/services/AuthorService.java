package pt.psoft.g1.psoftg1;

import java.util.Optional;

public interface AuthorService {

    Iterable<Author> findAll();

    Optional<Author> findOne(Name name);

    Author create(CreateAuthorRequest resource);

    Author partialUpdate(Autors authornumber, EditAuthorRequest resource, long parseLong);

    /* VER DUVIDAS NO CONTROLLER, CONSOANTE VEREDITO APAGA-SE OU NAO*/
    /* |
       v  */

    Author update(Autors authornumber, EditAuthorRequest resource, long desiredVersion);


}
