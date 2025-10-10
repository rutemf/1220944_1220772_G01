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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class UserNoSQL implements UserDetails {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    private boolean enabled = true;
    private String username;
    private String password;
    private Name name;
    private Set<Role> authorities = new HashSet<>();

    public UserNoSQL(User user) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdNoSQL();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
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
        user.setName(this.name);
        user.getAuthorities().addAll(this.authorities);
        user.setEnabled(this.enabled);
        return user;
    }

    public static UserNoSQL fromDomain(User user) {
        return new UserNoSQL(user);
    }
}
