package pt.psoft.g1.psoftg1.readermanagement.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;

import java.util.Optional;

/**
 *
 */
public interface ReaderRepository {
    Optional<Reader> findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);
    Optional<Reader> findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);
    Reader save(Reader reader);
    Iterable<Reader> findAll();
/*
    Optional<Reader> updateReader(@Param("readerNumber") @NotNull String readerNumber, @Param("readerName") String name, @Param("readerPhoneNumber") String phoneNumber, @Param("birthDate") LocalDate birthDate, @Param("marketing") boolean marketing, @Param("thirdParty") boolean thirdParty);
*/
/*
    void updateUser(@Param("userNumber") @NotNull Long userNumber, @Param("username") String username, @Param("password") String password);
*/
}
