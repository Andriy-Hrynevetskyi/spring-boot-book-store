package bookstore.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MAP_TIME_KEY = "timestamp";
    private static final String MAP_STATUS_KEY = "status";
    private static final String MAP_ERROR_KEY = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MAP_TIME_KEY, LocalDateTime.now());
        body.put(MAP_STATUS_KEY, HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        body.put(MAP_ERROR_KEY, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + " " + message;
        }
        return e.getDefaultMessage();
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CartItemException.class})
    protected ResponseEntity<Object> handleCartItemException(CartItemException ex) {
        return getResponseEntity(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({RegistrationException.class})
    protected ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_ACCEPTABLE);
    }

    private ResponseEntity<Object> getResponseEntity(Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MAP_TIME_KEY, LocalDateTime.now());
        body.put(MAP_STATUS_KEY, status);
        body.put(MAP_ERROR_KEY, ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
