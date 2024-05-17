package pt.psoft.g1.psoftg1.readermanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

/**
 * Brief guide:
 * <a href="https://www.baeldung.com/mapstruct">https://www.baeldung.com/mapstruct</a>
 * */
@Mapper(componentModel = "spring", uses = {ReaderService.class, UserService.class})
public abstract class ReaderMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "fullName")
    public abstract Reader createReader(CreateReaderRequest request);

    public abstract ReaderDetails createReaderDetails(int readerNumber, Reader reader, CreateReaderRequest request);

}
