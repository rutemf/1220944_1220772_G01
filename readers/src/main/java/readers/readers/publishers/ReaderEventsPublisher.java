package readers.readers.publishers;

import readers.readers.services.CreateReaderRequest;

public interface ReaderEventsPublisher {
    void sendReaderCreated(CreateReaderRequest request, String readerId);
}
