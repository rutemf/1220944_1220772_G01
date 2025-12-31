package auth_users.users.api;

import auth_users.users.model.User;
import auth_users.users.services.CreateUserRequest;
import auth_users.users.services.EditUserRequest;
import auth_users.users.services.SearchUsersQuery;
import auth_users.users.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import auth_users.shared.api.ListResponse;
import auth_users.shared.services.SearchRequest;
import auth_users.users.model.Role;

import java.util.List;

@Tag(name = "UserAdmin")
@RestController
@RequestMapping(path = "api/admin/users")
@RolesAllowed(Role.ADMIN)
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;
    private final UserViewMapper userViewMapper;

    @PostMapping
    public UserView create(@RequestBody @Valid final CreateUserRequest request) {
        final var user = userService.create(request);
        return userViewMapper.toUserView(user);
    }

    @PutMapping("{id}")
    public UserView update(@PathVariable final Long id, @RequestBody @Valid final EditUserRequest request) {
        final var user = userService.update(id, request);
        return userViewMapper.toUserView(user);
    }

    @DeleteMapping("{id}")
    public UserView delete(@PathVariable final Long id) {
        final var user = userService.delete(id);
        return userViewMapper.toUserView(user);
    }

    @GetMapping("{id}")
    public UserView get(@PathVariable final Long id) {
        final var user = userService.getUser(id);
        return userViewMapper.toUserView(user);
    }

    @PostMapping("search")
    public ListResponse<UserView> search(@RequestBody final SearchRequest<SearchUsersQuery> request) {
        final List<User> searchUsers = userService.searchUsers(request.getPage(), request.getQuery());
        return new ListResponse<>(userViewMapper.toUserView(searchUsers));
    }
}