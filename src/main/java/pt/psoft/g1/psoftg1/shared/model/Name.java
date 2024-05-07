package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
@Embeddable
public class Name {
    @NotNull
    @NotBlank
    @Column(name="NAME", length = 150)
    String name;

    //TODO: Move this to a properties file
    @Transient
    private static final List<String> FORBIDDEN_NAMES = List.of(new String[]{"Cocó", "Xixi"});

    public Name(String name){
        setName(name);
    }

    public void setName(String name){
        if(name == null)
            throw new IllegalArgumentException("Name cannot be null");
        if(name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank, nor only white spaces");
        if(!StringUtilsCustom.isAlphanumeric(name))
            throw new IllegalArgumentException("Name can only contain alphanumeric characters");

        for(String forbidden : FORBIDDEN_NAMES){
            if(name.contains(forbidden))
                throw new IllegalArgumentException("Name contains forbidden word");
        }
        this.name = name;
    }
    public String toString() {
        return this.name;
    }

    protected Name() {
        // for ORM only
    }
}
