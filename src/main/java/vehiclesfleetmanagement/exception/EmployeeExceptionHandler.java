package vehiclesfleetmanagement.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  private ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", e.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IOException.class)
  private ResponseEntity<Object> handleIOException(IOException e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("error", "File not found: " + e.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}
