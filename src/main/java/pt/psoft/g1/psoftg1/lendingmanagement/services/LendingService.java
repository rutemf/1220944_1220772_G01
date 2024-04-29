package pt.psoft.g1.psoftg1.lendingmanagement.services;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

import java.util.Optional;

public interface LendingService {
    Iterable<Lending> findAll();

    Optional<Lending> findByLendingNumber(String lendingNumber);

    Lending create(CreateLendingRequest resource); //No ID passed, as it is auto generated

    Lending update(String id, EditLendingRequest resource);



}
