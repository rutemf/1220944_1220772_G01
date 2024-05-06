package pt.psoft.g1.psoftg1.readermanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

/**
 * Brief guide:
 * <a href="https://www.baeldung.com/mapstruct">https://www.baeldung.com/mapstruct</a>
 * */
@Mapper(componentModel = "spring", uses = {ReaderService.class, UserService.class})
public abstract class ReaderMapper {

    /**
     * <p>FROM CHATGPT, ON WHY THIS METHOD WILL NOT BE USED:</p>
     * In this method:
     *
     * <p>- The update method takes a SetLendingReturnedDto object and a Lending object.
     * <p>- It sets the return date and commentary of the Lending object based on the values from the DTO.
     * <p>
     * The method doesn't return anything (void), as it directly updates the provided Lending object in place.
     * <p>
     * The logic inside the setReturnDate, setReturned, and getDaysDelayed methods will only be executed if
     * the corresponding methods are called explicitly. In the provided LendingMapper class, the update method
     * only sets values from the SetLendingReturnedDto to the Lending object and does not call these methods.
     * <p>
     * So, based on the provided code and assuming no other code calls these methods directly, the logic inside
     * these methods will not be reached by the LendingMapper class. If you want these methods to be executed
     * as part of the mapping process, you would need to explicitly call them within the update method of the
     * LendingMapper class. However, it's generally not recommended to include such business logic directly in
     * the mapper class. Instead, you might consider moving this logic to the service layer where the business
     * operations are performed.*/
    public abstract void update(SetReaderReturnedDto request, @MappingTarget Reader reader);

}
