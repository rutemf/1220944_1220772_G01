package pt.psoft.g1.psoftg1.authormanagement.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateAuthorRequest extends UpdateAuthorRequest {
    public CreateAuthorRequest(final String name, final String bio){
        super(name, bio);
    }

}
