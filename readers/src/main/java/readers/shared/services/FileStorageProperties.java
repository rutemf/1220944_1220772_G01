package readers.shared.services;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Component
@Data
public class FileStorageProperties {
    private String uploadDir;
    private long photoMaxSize;
}