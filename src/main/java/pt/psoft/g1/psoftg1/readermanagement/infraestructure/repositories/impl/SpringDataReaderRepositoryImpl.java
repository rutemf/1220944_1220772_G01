package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface SpringDataReaderRepositoryImpl extends ReaderRepository, CrudRepository<Reader, ReaderNumber> {
    @Override
    @Query("SELECT r FROM Reader r WHERE r.phoneNumber = :phoneNumber")
    Optional<Reader> findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);

    @Override
    @Query("SELECT r FROM Reader r WHERE r.readerNumber = :readerNumber")
    Optional<Reader> findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);

    @Override
    @Query("SELECT r FROM Reader r")
    public List<Reader> findAll();

/*
    @Override
    @Modifying
    @Query("UPDATE Reader r SET "+
            "r.fullName = COALESCE(:name, r.fullName), " +
            "r.phoneNumber = COALESCE(:phoneNumber, r.phoneNumber), " +
            "r.birthDate = COALESCE(:birthDate, r.birthDate), " +
            "r.marketingConsent = COALESCE(:marketing, r.marketingConsent), " +
            "r.thirdPartySharingConsent = COALESCE(:thirdParty, r.thirdPartySharingConsent) " +
            "WHERE r.readerNumber = :readerNumber")
    public Optional<Reader> updateReader(@Param("readerNumber") @NotNull String readerNumber, @Param("readerName") String name, @Param("readerPhoneNumber") String phoneNumber, @Param("birthDate") LocalDate birthDate, @Param("marketing") boolean marketing, @Param("thirdParty") boolean thirdParty);
*/

    @Override
    @Query("UPDATE User u SET " +
            "u.username = COALESCE(:username, u.username), "+
            "u.password = COALESCE(:password, u.password) "+
            "WHERE u.id = :userNumber")
    public void updateUser(@Param("userNumber") @NotNull Long userNumber, @Param("username") String username, @Param("password") String password);


}
