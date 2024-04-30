package pt.psoft.g1.psoftg1.lendingmanagement.services;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

public interface LendingService {
    Iterable<Lending> findAll();

    Lending create(CreateLendingDto resource); //No ID passed, as it is auto generated

    Lending setReturned(String id, SetLendingReturnedDto resource, long desiredVersion);



}
