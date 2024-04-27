package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import java.io.Serializable;


@Embeddable
public class Isbn implements Serializable {
    @Size(min = 1, max = 16)
    String isbn;

}
