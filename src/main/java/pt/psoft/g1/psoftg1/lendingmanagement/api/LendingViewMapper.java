package pt.psoft.g1.psoftg1.lendingmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

/**
 * Brief guide:
 * <a href="https://www.baeldung.com/mapstruct">https://www.baeldung.com/mapstruct</a>
 * */
@Mapper(componentModel = "spring")
public abstract class LendingViewMapper {

    public abstract LendingView toLendingView(Lending lending);

    public abstract Iterable<LendingView> toLendingView(Iterable<Lending> lendings);

    public String map(LendingNumber value) {return value.toString();}

    public String map(Title value) {return value.toString();}

}
