package test.security.security_test.utill.exception;

import lombok.Getter;
import test.security.security_test.dto.Response;

@Getter
public class RestException extends Exception{

    private final Response response;

    public RestException(int code, String message) {
        response = new Response.ResponseBuilder<>(message ,code).build();
    }
}
