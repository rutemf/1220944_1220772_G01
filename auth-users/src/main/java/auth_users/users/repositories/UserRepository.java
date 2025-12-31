package auth_users.users.repositories;

import java.util.List;
import java.util.Optional;

import auth_users.exceptions.NotFoundException;
import auth_users.users.services.SearchUsersQuery;
import auth_users.users.model.User;
import auth_users.shared.services.Page;

public interface UserRepository {

    <S extends User> List<S> saveAll(Iterable<S> entities);

    <S extends User> S save(S entity);

    Optional<User> findById(Long objectId);

    default User getById(final Long id) {
        final Optional<User> maybeUser = findById(id);
        // throws 404 Not Found if the user does not exist or is not enabled
        return maybeUser.filter(User::isEnabled).orElseThrow(() -> new NotFoundException(User.class, id));
    }

    Optional<User> findByUsername(String username);

    List<User> searchUsers(Page page, SearchUsersQuery query);

    List<User> findByNameName(String name);

    List<User> findByNameNameContains(String name);

    void delete(User user);

    void deleteAll();
}
