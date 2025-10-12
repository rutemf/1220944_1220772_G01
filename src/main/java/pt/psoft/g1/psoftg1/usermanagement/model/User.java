package pt.psoft.g1.psoftg1.usermanagement.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.psoft.g1.psoftg1.shared.model.Name;

import lombok.Getter;

@Getter
public class User implements UserDetails {

    @Setter
    private boolean enabled = true;

    @Setter
    private String username;

    private String password;

    @Setter
    private Name name;

    private final Set<Role> authorities = new HashSet<>();

    public User(final String username, final String password) {
        this.username = username;
        setPassword(password);
    }

    public User(final String username, final String password, Name name, Set<Role> authorities) {
        this.username = username;
        setPassword(password);
        this.name = name;
        if (authorities != null) {
            this.authorities.addAll(authorities);
        }
    }

    public User() {}

    public void setPassword(final String password) {
        new Password(password);
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void addAuthority(final Role role) {
        this.authorities.add(role);
    }

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
}
