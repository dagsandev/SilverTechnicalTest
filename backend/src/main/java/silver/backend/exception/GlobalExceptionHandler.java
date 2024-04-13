package silver.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
