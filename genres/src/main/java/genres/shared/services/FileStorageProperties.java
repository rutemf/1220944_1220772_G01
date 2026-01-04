package genres.shared.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "file")
@Component
@Data
public class FileStorageProperties {
    private String uploadDir;
    private long photoMaxSize;
}
