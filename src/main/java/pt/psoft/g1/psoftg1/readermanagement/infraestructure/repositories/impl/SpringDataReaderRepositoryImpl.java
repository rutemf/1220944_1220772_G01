package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;

import java.util.ArrayList;
import java.util.List;


public interface SpringDataReaderRepositoryImpl extends ReaderRepository, CrudRepository<Reader, ReaderNumber> {
    @Override
    @Query("SELECT r FROM Reader r WHERE r.phoneNumber = :phoneNumber")
    Reader findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);

    @Override
    @Query("SELECT r FROM Reader r WHERE r.readerNumber = :readerNumber")
    Reader findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);

    @Override
    @Query("SELECT r FROM Reader r")
    public List<Reader> findAll();
}
