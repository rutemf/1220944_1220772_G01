package readers.readers.repositories;

import readers.readers.model.ReaderDetails;

public interface ReaderRepository {
    Long count();
    ReaderDetails save(ReaderDetails readerDetails);
}
