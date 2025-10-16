package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.IDBase65GeneratorService;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "forbidden_names")
public class ForbiddenNameNoSQL {

    @Transient
    private IDGeneratorService idGeneratorService;

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameNoSQL(ForbiddenName forbiddenName) {
        this.id = idGeneratorService.generateId();
        this.forbiddenName = forbiddenName.getForbiddenName();
    }

    // MongoDB
    protected ForbiddenNameNoSQL() {}

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }

    public static ForbiddenNameSQL fromDomain(ForbiddenName forbiddenName) {
        return new ForbiddenNameSQL(forbiddenName);
    }
}
