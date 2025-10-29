package pt.psoft.g1.psoftg1.usermanagement.services;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
public class CreateUserRequest {

	@NonNull
	@NotBlank
	@Email
	@Setter
	@Getter
	private String username;

	@NonNull
	@NotBlank
	@Setter
	@Getter
	private String password;

	@NonNull
	@NotBlank
	private String name;

	@Getter
	@Setter
	private String role;

	private Set<String> authorities = new HashSet<>();

	public CreateUserRequest(final @NonNull String username, final @NonNull String fullName, final @NonNull String password) {
		this.username = username;
		this.name = fullName;
		this.password = password;
	}
}