package readers.readers.services;

import readers.readers.model.ReaderDetails;

public interface ReaderService {
    ReaderDetails create(CreateReaderRequest request);
    ReaderDetails findByReaderNumber(String readerNumber);
    ReaderDetails create (ReaderViewAMQP readerViewAMQP);
}
