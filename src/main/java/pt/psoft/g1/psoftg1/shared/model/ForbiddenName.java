package pt.psoft.g1.psoftg1.shared.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForbiddenName{

    private String forbiddenName;

    public ForbiddenName(String name) {
        this.forbiddenName = name;
    }
}
