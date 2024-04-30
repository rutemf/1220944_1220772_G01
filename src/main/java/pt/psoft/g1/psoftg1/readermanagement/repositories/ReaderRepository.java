package pt.psoft.g1.psoftg1.readermanagement.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ReaderRepository {
    Optional<Reader> findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);
    Optional<Reader> findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);
    Reader save(Reader reader);
    List<Reader> findAll();
    public Optional<Reader> updateReader(@Param("readerNumber") @NotNull String readerNumber, @Param("readerName") String name, @Param("readerPhoneNumber") String phoneNumber, @Param("birthDate") LocalDate birthDate, @Param("marketing") boolean marketing, @Param("thirdParty") boolean thirdParty);
    public void updateUser(@Param("userNumber") @NotNull Long userNumber, @Param("username") String username, @Param("password") String password);
}
