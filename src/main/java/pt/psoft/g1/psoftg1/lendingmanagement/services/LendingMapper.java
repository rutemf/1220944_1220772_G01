package pt.psoft.g1.psoftg1.lendingmanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;

@Mapper(componentModel = "spring", uses = {BookService.class, ReaderService.class})
public abstract class LendingMapper {

    public abstract void update(SetLendingReturnedDto request, @MappingTarget Lending lending);

}
