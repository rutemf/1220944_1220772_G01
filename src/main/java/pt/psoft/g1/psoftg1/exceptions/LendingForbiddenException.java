package pt.psoft.g1.psoftg1.exceptions;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class LendingForbiddenException extends ValidationException {

    private static final long serialVersionUID = 1L;

    public LendingForbiddenException(final String string) {
        super(string);
    }


}
