package genres.shared.model;

import genres.shared.api.UploadFileResponse;
import genres.shared.services.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileUtils {

    public static UploadFileResponse doUploadFile(FileStorageService fileStorageService, String id, final MultipartFile file) throws Exception {
        if (fileStorageService == null || id == null || file == null) {
            throw new Exception("Could not get reference of fileStorageService, id or file");
        }

        final String fileName = fileStorageService.storeFile(id, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(fileName).toUriString();

        fileDownloadUri = fileDownloadUri.replace("/photos/", "/photo/");

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }
}
