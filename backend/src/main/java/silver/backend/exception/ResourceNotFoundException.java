package silver.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private Object fieldValue;

    public ResourceNotFoundException (String resource, Object fieldValue){
        super(String.format("%s with id %s was not found", resource, fieldValue));
        this.resource = resource;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException (String resource){
        super(String.format("%s was not found", resource));
        this.resource = resource;
    }

}
