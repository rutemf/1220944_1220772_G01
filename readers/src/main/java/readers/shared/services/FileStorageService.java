package readers.shared.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import readers.exceptions.FileStorageException;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private long photoMaxSize;
    private final String[] validImageFormats = { "image/png", "image/jpeg" };

    @Autowired
    public FileStorageService(final FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.photoMaxSize = fileStorageProperties.getPhotoMaxSize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (final Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public String storeFile(final String prefix, final MultipartFile file) {
        // final String fileName = prefix + "_" + determineFileName(file);
        // files will contain only the generated uuid passed as prefix
        final String fileName = prefix + "." + getExtension(file.getOriginalFilename()).orElse("");

        // Copy file to the target location (Replacing existing file with the same name)
        try {
            final Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Optional<String> getExtension(final String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
