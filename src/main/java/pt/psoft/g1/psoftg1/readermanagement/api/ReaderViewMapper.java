package pt.psoft.g1.psoftg1.readermanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper extends MapperInterface {

    @Mapping(target = "fullName", source = "reader.name.name")
    @Mapping(target = "email", source = "reader.username")
    @Mapping(target = "birthDate", source = "birthDate.date")
    //@Mapping(target = "phoneNumber", source = "phoneNumber.number")
    @Mapping(target = "gdprConsent", source = "gdprConsent")
    @Mapping(target = "readerNumber", source = "readerNumber")
    @Mapping(target = "photo", expression = "java(generatePhotoUrl(readerDetails))")
    public abstract ReaderView toReaderView(ReaderDetails readerDetails);

    public abstract List<ReaderView> toReaderView(Iterable<ReaderDetails> readerList);

   @Mapping(target = "readerView", source = "readerDetails")
    public abstract ReaderCountView toReaderCountView(ReaderBookCountDTO readerBookCountDTO);

    public abstract List<ReaderCountView> toReaderCountViewList(List<ReaderBookCountDTO> readerBookCountDTOList);


    protected String generatePhotoUrl(ReaderDetails readerDetails) {
        String readerNumber = readerDetails.getReaderNumber();
        String[] readerNumberSplit = readerNumber.split("/");
        int year = Integer.parseInt(readerNumberSplit[0]);
        int seq = Integer.parseInt(readerNumberSplit[1]);
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/readers/{year}/{seq}/photo").buildAndExpand(year,seq).toUri().toString();
    }
}
