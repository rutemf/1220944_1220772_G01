package pt.psoft.g1.psoftg1.authormanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCountDTO {
    private Author author;
    private long lendingCount;

}

