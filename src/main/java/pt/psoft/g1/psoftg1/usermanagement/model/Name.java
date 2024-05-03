package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Embeddable
public class Name {
    @NotNull
    @NotBlank
    String fullName;

    //TODO: Move this to a properties file
    @Transient
    private static final List<String> FORBIDDEN_NAMES = List.of(new String[]{"Cocó", "Xixi"});

    public Name(String fullName){
        setFullName(fullName);
    }

    public void setFullName(String fullName){
        for(String forbidden : FORBIDDEN_NAMES){
            if(fullName.contains(forbidden)){
                throw new IllegalArgumentException("Name contains forbidden word");
            }
        }
        this.fullName = fullName;
    }
    public String toString() {
        return this.fullName;
    }

    protected Name() {
        // for ORM only
    }
}
