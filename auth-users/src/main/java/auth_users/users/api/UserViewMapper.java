package auth_users.users.api;

import java.util.List;

import auth_users.users.model.User;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Mapping(target = "fullName", source = "name.name")
    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);
}
