package pt.psoft.g1.psoftg1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class LendingForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LendingForbiddenException(final String string) {
        super(string);
    }


}
