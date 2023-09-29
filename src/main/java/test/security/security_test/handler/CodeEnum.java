package test.security.security_test.handler;

import com.fasterxml.jackson.annotation.JsonValue;

public interface CodeEnum {

    @JsonValue
    String getCode();
}
