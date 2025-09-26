package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
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
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public Optional<User> findById(Long objectId) {
        return Optional.ofNullable(entityManager.find(User.class, objectId));
    }

    @Override
    public User getById(Long id) {
        return findById(id).filter(User::isEnabled)
        .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
        "SELECT u FROM User u WHERE u.username = :username", User.class);

        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        return List.of();
    }

    @Override
    public List<User> findByNameName(String name) {
        TypedQuery<User> query = entityManager.createQuery(
        "SELECT u FROM User u WHERE u.name.name = :name", User.class);

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        TypedQuery<User> query = entityManager.createQuery(
        "SELECT u FROM User u WHERE u.name.name LIKE :name", User.class);

        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public void delete(User user) {
        User managed = entityManager.contains(user) ? user : entityManager.merge(user);
        entityManager.remove(managed);
    }
}
