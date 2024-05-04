package pt.psoft.g1.psoftg1.authormanagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private List<Author> authorList = new ArrayList<>();
    @Override
    public List<Author> findAll() {
        return authorList;
    }
    @Override
    public Author save(Author author) throws Exception {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null.");
        }

        if (exists(author)) {
            throw new Exception("A author with the name '" + author.toString() + "' is already registered.");
        }

        try {
            authorList.add(author);
        } catch (Exception e) {
            throw new Exception("Unable to save the author: " + e.getMessage(), e);
        }
        return author;
    }
    private boolean exists(Author author) {
        for (Author a : authorList) {
            if (a.toString().equals(author.toString())) {
                return true;
            }
        }
        return false;
    }

    //todo: fazer daqui para baixo
    private AuthorRepository authorRepository;


    @Override
    public Optional<Author> findOne(Name name) {
        return Optional.empty();
    }

    @Override
    public Author partialUpdate(Long authornumber, UpdateAuthorRequest resource, long parseLong) {
        return null;
    }

    @Override
    public Author update(Long authornumber, UpdateAuthorRequest resource, long desiredVersion) {
        return null;
    }

    @Override
    public Optional<Author> findOne(final Long authorNumber) {
        //return authorRepository.findByAuthorNumber(authorNumber);
        return Optional.empty();
    }

    @Override
    public Author create(final CreateAuthorRequest resource) {
        //final Author author = EditAuthorMapper.create(resource);
        //return authorRepository.save(author);
        return null;
    }

}
