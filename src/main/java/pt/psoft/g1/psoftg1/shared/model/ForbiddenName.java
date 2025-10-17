package pt.psoft.g1.psoftg1.shared.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ForbiddenName implements Serializable {

    private String forbiddenName;

    public ForbiddenName(String name) {
        this.forbiddenName = name;
    }
}
