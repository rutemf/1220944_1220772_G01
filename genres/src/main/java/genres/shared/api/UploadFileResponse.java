package genres.shared.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Metadata about the uploaded file")
@Getter
public class UploadFileResponse {

    private final String fileName;

    @Schema(description = "Absolute URL of the file")
    private final String fileDownloadUri;

    @Schema(description = "Media type")
    private final String fileType;

    @Schema(description = "File size in bytes")
    private final long size;

    public UploadFileResponse(final String fileName, final String fileDownloadUri, final String fileType, final long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}