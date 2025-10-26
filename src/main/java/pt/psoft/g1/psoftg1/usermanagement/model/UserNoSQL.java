package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "user")
public class UserNoSQL implements UserDetails {

    @Id
    private String id;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String modifiedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    private boolean enabled = true;
    private String username;
    private String password;
    private String name;
    private Set<Role> authorities = new HashSet<>();

    public UserNoSQL(User user) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName().toString();
        this.authorities = new HashSet<>(user.getAuthorities());
        this.enabled = user.isEnabled();
    }

    // NoSQL
    protected UserNoSQL() { }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User toDomain() {
        User user = new User(this.username, this.password);
        user.setName(new Name(this.name));
        user.getAuthorities().addAll(this.authorities);
        user.setEnabled(this.enabled);
        return user;
    }

    public void addAuthority(final Role authority) {
        this.authorities.add(authority);
    }

    public static UserNoSQL fromDomain(User user) {
        return new UserNoSQL(user);
    }
}
