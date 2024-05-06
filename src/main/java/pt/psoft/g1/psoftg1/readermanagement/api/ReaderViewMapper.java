package pt.psoft.g1.psoftg1.readermanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.api.ViewMapper;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper extends ViewMapper {

    @Mapping(target = "fullName", source = "user.name")
    @Mapping(target = "email", source = "user.username")
    @Mapping(target = "birthDate", source = "birthDate.date")
    public abstract ReaderView toReaderView(ReaderDetails readerDetails);

    public abstract Iterable<ReaderView> toReaderView(Iterable<ReaderDetails> readerList);

}
