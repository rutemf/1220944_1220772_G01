package pt.psoft.g1.psoftg1.readermanagement.api;

import org.mapstruct.Mapper;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper {

    public abstract ReaderView toReaderView(Reader reader);

    public abstract List<ReaderView> toReaderView(List<Reader> readerList);

    public Integer mapOptInt(final Optional<Integer> i) {
        return i.orElse(null);
    }

    public Long mapOptLong(final Optional<Long> i) {
        return i.orElse(null);
    }

    public String mapOptString(final Optional<String> i) {
        return i.orElse(null);
    }

    public String map(ReaderNumber readerNumber) {
        return readerNumber.toString();
    }

    public String map(PhoneNumber phoneNumber) {
        return phoneNumber.toString();
    }
}
