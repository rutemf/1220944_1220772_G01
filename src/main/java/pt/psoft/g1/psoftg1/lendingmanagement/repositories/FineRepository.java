package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;

public interface FineRepository {
    Iterable<Fine> findAll();

    Fine save(Fine fine);

}
