package pt.psoft.g1.psoftg1.usermanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final EditUserMapper userEditMapper;

    private final ForbiddenNameRepository forbiddenNameRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findByName(String name) {
        return this.userRepo.findByNameName(name);
    }

    public List<User> findByNameLike(String name) {
        return this.userRepo.findByNameNameContains(name);
    }

    @Transactional
    public User create(final CreateUserRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists!");
        }

        Iterable<String> words = List.of(request.getName().split("\\s+"));
        for (String word : words) {
            if (!forbiddenNameRepository.findByForbiddenNameIsContained(word).isEmpty()) {
                throw new IllegalArgumentException("Name contains a forbidden word");
            }
        }

        User user = new User(request.getUsername(), request.getPassword(), request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addAuthority(new Role(Role.ADMIN));

        return userRepo.save(user);
    }

    @Transactional
    public User update(final Long id, final EditUserRequest request) {
        final User user = userRepo.getById(id);
        userEditMapper.update(request, user);

        return userRepo.save(user);
    }

    @Transactional
    public User delete(final Long id) {
        final User user = userRepo.getById(id);

        // user.setUsername(user.getUsername().replace("@", String.format("_%s@",
        // user.getId().toString())));
        user.setEnabled(false);
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        System.out.println(username);
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username - %s, not found", username)));
    }

    public boolean usernameExists(final String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public User getUser(final Long id) {
        return userRepo.getById(id);
    }

    public Optional<User> findByUsername(final String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        if (page == null) {
            page = new Page(1, 10);
        }
        if (query == null) {
            query = new SearchUsersQuery("", "");
        }
        return userRepo.searchUsers(page, query);
    }

    public User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new AccessDeniedException("User is not logged in");
        }

        // split is present because jwt is storing the id before the username, separated by a comma
        String loggedUsername = jwt.getClaimAsString("sub").split(",")[1];

        Optional<User> loggedUser = findByUsername(loggedUsername);
        if (loggedUser.isEmpty()) {
            throw new AccessDeniedException("User is not logged in");
        }

        return loggedUser.get();
    }
}
