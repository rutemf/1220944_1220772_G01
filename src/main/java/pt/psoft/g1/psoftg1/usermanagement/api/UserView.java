package pt.psoft.g1.psoftg1.usermanagement.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

	private String id;
	private String username;
	private String fullName;

}