package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.*;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class UserRepositorySQL implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();

        for (S entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Override
    public <S extends User> S save(S entity) {
        if (entity instanceof Reader reader) {
            ReaderSQL readerSQL = ReaderSQL.fromDomain(reader);
            ReaderSQL saved = entityManager.merge(readerSQL);
            return (S) saved.toDomain();
        } else if (entity instanceof Librarian librarian) {
            LibrarianSQL librarianSQL = LibrarianSQL.fromDomain(librarian);
            LibrarianSQL saved = entityManager.merge(librarianSQL);
            return (S) saved.toDomain();
        } else {
            UserSQL userSQL = UserSQL.fromDomain(entity);
            UserSQL saved = entityManager.merge(userSQL);
            return (S) saved.toDomain();
        }
    }

    @Override
    public Optional<User> findById(Long objectId) {
        UserSQL userSQL = entityManager.find(UserSQL.class, objectId);
        return Optional.ofNullable(userSQL).map(UserSQL::toDomain);
    }

    @Override
    public User getById(Long id) {
        return findById(id).filter(User::isEnabled)
        .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<UserSQL> query = entityManager.createQuery(
        "SELECT u FROM UserSQL u WHERE u.username = :username", UserSQL.class);

        query.setParameter("username", username);
        return query.getResultStream().findFirst().map(UserSQL::toDomain);
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        StringBuilder jpql = new StringBuilder("SELECT u FROM UserSQL u WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (query.getUsername() != null && !query.getUsername().isBlank()) {
            jpql.append(" AND u.username LIKE ?1").append(1);
            params.add("%" + query.getUsername() + "%");
        }
        if (query.getFullName() != null && !query.getFullName().isBlank()) {
            jpql.append(" AND u.name.name LIKE ?").append(params.size() + 1);
            params.add("%" + query.getFullName() + "%");
        }

        TypedQuery<UserSQL> typedQuery = entityManager.createQuery(jpql.toString(), UserSQL.class);
        for (int i = 0; i < params.size(); i++) {
            typedQuery.setParameter(i + 1, params.get(i));
        }

        typedQuery.setFirstResult((page.getNumber() - 1) * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList().stream().map(UserSQL::toDomain).toList();
    }

    @Override
    public List<User> findByNameName(String name) {
        TypedQuery<UserSQL> query = entityManager.createQuery(
        "SELECT u FROM UserSQL u WHERE u.name = :name", UserSQL.class);

        query.setParameter("name", name);
        return query.getResultList().stream().map(UserSQL::toDomain).toList();
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        TypedQuery<UserSQL> query = entityManager.createQuery(
        "SELECT u FROM UserSQL u WHERE u.name LIKE :name", UserSQL.class);

        query.setParameter("name", "%" + name + "%");
        return query.getResultList().stream().map(UserSQL::toDomain).toList();
    }

    @Override
    public void delete(User user) {
        UserSQL userSQL = UserSQL.fromDomain(user);
        UserSQL managed = entityManager.contains(userSQL) ? userSQL : entityManager.merge(userSQL);
        entityManager.remove(managed);
    }
}
