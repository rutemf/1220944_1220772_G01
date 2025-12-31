package genres.users.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

@Value
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ADMIN = "ADMIN";
    public static final String LIBRARIAN = "LIBRARIAN";
    public static final String READER = "READER";

    String authority;
}