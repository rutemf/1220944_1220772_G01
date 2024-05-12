package pt.psoft.g1.psoftg1.readermanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper extends MapperInterface {

    @Mapping(target = "fullName", source = "user.name.name")
    @Mapping(target = "email", source = "user.username")
    @Mapping(target = "birthDate", source = "birthDate.date")
    @Mapping(target = "gdprConsent", source = "gdprConsent")
    @Mapping(target = "readerNumber", source = "readerNumber")
    public abstract ReaderView toReaderView(ReaderDetails readerDetails);

    public abstract List<ReaderView> toReaderView(Iterable<ReaderDetails> readerList);

}
