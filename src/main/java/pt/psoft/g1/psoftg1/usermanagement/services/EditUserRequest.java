package pt.psoft.g1.psoftg1.usermanagement.services;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {

	private String name;
	private String username;
	private String password;
	private Set<String> authorities;

}