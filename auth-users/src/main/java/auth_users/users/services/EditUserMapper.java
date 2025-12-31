package auth_users.users.services;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;

import auth_users.users.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import auth_users.shared.api.MapperInterface;
import auth_users.users.model.Role;

@Mapper(componentModel = "spring")
public abstract class EditUserMapper extends MapperInterface {

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
    public abstract User create(CreateUserRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
    public abstract void update(EditUserRequest request, @MappingTarget User user);

    @Named("stringToRole")
    protected Set<Role> stringToRole(final Set<String> authorities) {
        if (authorities != null) {
            return authorities.stream().map(Role::new).collect(toSet());
        }
        return new HashSet<>();
    }
}