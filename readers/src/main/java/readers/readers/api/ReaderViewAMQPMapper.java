package readers.readers.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import readers.genres.model.Genre;
import readers.readers.model.BirthDate;
import readers.readers.model.PhoneNumber;
import readers.readers.model.ReaderDetails;
import readers.readers.model.ReaderNumber;
import readers.readers.services.ReaderView;
import readers.readers.services.ReaderViewAMQP;
import readers.shared.api.MapperInterface;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ReaderViewAMQPMapper extends MapperInterface {


    @Mapping(target = "readerNumber", expression = "java(mapReaderNumber(reader.getReaderNumber()))")
    @Mapping(target = "birthDate", expression = "java(mapBirthDate(reader.getBirthDate()))")
    @Mapping(target = "phoneNumber", expression = "java(mapPhoneNumber(reader.getPhoneNumber()))")
    @Mapping(target = "gdpr", source = "gdprConsent")
    @Mapping(target = "interestList", expression = "java(mapGenres(reader.getInterestList()))")
    public abstract ReaderViewAMQP toReaderViewAMQP(ReaderDetails reader);

    public abstract List<ReaderViewAMQP> toReaderViewAMQP(List<ReaderDetails> readers);

    protected List<String> mapGenres(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getGenre)
                .collect(Collectors.toList());
    }

    protected String mapReaderNumber(ReaderNumber rn) {
        return rn == null ? null : String.valueOf(rn.getReaderNumber());
    }

    protected String mapBirthDate(BirthDate bd) {
        return bd == null ? null : String.valueOf(bd.getBirthDate());
    }

    protected String mapPhoneNumber(PhoneNumber pn) {
        return pn == null ? null : pn.getPhoneNumber();
    }


}
