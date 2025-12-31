package authors.authors.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@Schema(description = "An Author for AMQP communication")
@NoArgsConstructor
public class AuthorsViewAMQP {

    @NotNull
    private Long authorNumber;

    @NotNull
    private String name;

    private String bio;

    @NotNull
    private Long version;

    @Setter
    @Getter
    private Map<String, Object> _links = new HashMap<>();

    public AuthorsViewAMQP(Long authorNumber,
                           String name,
                           String bio,
                           Long version) {
        this.authorNumber = authorNumber;
        this.name = name;
        this.bio = bio;
        this.version = version;
    }
}
