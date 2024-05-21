package pt.psoft.g1.psoftg1.readermanagement.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ReaderRepository {
    Optional<ReaderDetails> findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);
    Optional<ReaderDetails> findByUsername(@Param("username") @NotNull String username);
    Optional<ReaderDetails> findByUserId(@Param("userId") @NotNull Long userId);
    int getCountFromCurrentYear();
    ReaderDetails save(ReaderDetails readerDetails);
    Iterable<ReaderDetails> findAll();
    Page<ReaderDetails> findTopReaders(Pageable pageable);
/*
    Optional<Reader> updateReader(@Param("readerNumber") @NotNull String readerNumber, @Param("readerName") String name, @Param("readerPhoneNumber") String phoneNumber, @Param("birthDate") LocalDate birthDate, @Param("marketing") boolean marketing, @Param("thirdParty") boolean thirdParty);
*/
/*
    void updateUser(@Param("userNumber") @NotNull Long userNumber, @Param("username") String username, @Param("password") String password);
*/
}
