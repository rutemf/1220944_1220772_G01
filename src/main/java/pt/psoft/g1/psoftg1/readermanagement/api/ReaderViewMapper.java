package pt.psoft.g1.psoftg1.readermanagement.api;

import org.mapstruct.Mapper;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.shared.api.ViewMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper extends ViewMapper {

    public abstract ReaderView toReaderView(Reader reader);

    public abstract List<ReaderView> toReaderView(List<Reader> readerList);

}
