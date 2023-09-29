package test.security.security_test.dto.authorization;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;

@Data
@Alias("authorizationRequest")
public class AuthorizationRequest {

    @NotNull
    private String id;
    @NotNull
    private String password;

}
