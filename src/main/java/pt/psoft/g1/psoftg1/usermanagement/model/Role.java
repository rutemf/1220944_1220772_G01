package pt.psoft.g1.psoftg1.usermanagement.model;

import org.springframework.security.core.GrantedAuthority;

public record Role(String authority) implements GrantedAuthority {

	public static final String ADMIN = "ADMIN";
	public static final String LIBRARIAN = "LIBRARIAN";
	public static final String READER = "READER";

	@Override
	public String getAuthority() {
		return authority;
	}
}