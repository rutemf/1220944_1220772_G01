package auth_users.shared.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListResponse<T> {
    private List<T> items;
}
