package auth_users.users.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAMQP {
    private String readerId;
    private String username;
    private String password;
    private String fullName;
    private String role;
}
