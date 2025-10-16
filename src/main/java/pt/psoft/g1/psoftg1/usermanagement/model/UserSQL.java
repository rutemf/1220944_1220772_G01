package pt.psoft.g1.psoftg1.usermanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class UserSQL implements UserDetails {

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

    @Column(unique = true)
    @Email
    private String username;
    private String password;

    private String name;

    @ElementCollection
    @JoinTable(name="user_authorities")
    private Set<Role> authorities = new HashSet<>();

    public UserSQL(User user) {
        this.id = IDGeneratorService.generateIdSQL();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName().toString();
        this.authorities = new HashSet<>(user.getAuthorities());
        this.enabled = user.isEnabled();
    }

    // JPA
    protected UserSQL() { }

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

    public void addAuthority(final Role authority) {
        this.authorities.add(authority);
    }

    public User toDomain() {
        User user = new User(this.username, this.password, this.name);
        user.getAuthorities().addAll(this.authorities);
        return user;
    }

    public static UserSQL fromDomain(User user) {
        return new UserSQL(user);
    }
}
