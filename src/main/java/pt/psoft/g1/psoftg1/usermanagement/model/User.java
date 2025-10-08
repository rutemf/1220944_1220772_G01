package pt.psoft.g1.psoftg1.usermanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.psoft.g1.psoftg1.shared.model.Name;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements UserDetails {

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;
	private boolean enabled = true;
	private String username;
	private String password;
	private Name name;
	private final Set<Role> authorities = new HashSet<>();

	public User(final String username, final String password) {
		this.username = username;
		setPassword(password);
	}

	public void setPassword(final String password) {
		new Password(password);
		final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);
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
}
