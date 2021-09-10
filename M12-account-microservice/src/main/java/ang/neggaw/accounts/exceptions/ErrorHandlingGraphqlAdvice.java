package ang.neggaw.accounts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ANG
 * since: 10/09/2021 09:49
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = { "ang.neggaw.accounts.graphqls" })
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseBody
public class ErrorHandlingGraphqlAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public List<Violation> onConstraintViolationException(ConstraintViolationException e) {

        ValidationErrorResponse.violations = new ArrayList<>();
        e.getConstraintViolations().forEach(cv -> ValidationErrorResponse.violations
                .add(new Violation(cv.getPropertyPath().toString(), cv.getMessage())));
        return ValidationErrorResponse.violations;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<Violation> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse.violations = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> ValidationErrorResponse.violations
                .add(new Violation(fieldError.getField(), fieldError.getDefaultMessage())));
        return ValidationErrorResponse.violations;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Violation {
        private String fieldName;
        private String errorMessage;
    }

    @Getter
    @Setter
    public static class ValidationErrorResponse {
        private static List<Violation> violations;
    }
}