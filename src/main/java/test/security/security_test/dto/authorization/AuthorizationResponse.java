package test.security.security_test.dto.authorization;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;

import java.util.List;


@Data
@Alias("authorizationResponse")
public class AuthorizationResponse {

    private long idx;
    private AuthoritiesEnum role;
    private List<GrantedAuthority> grantedAuth;
    private String token;
    private String name;

    private String password;

    private AuthorizationResponse(AuthoritiesEnum role, String token, String name) {
        this.role = role;
        this.token = token;
        setGrantedAuth(role);
        this.name = name;
        this.password = null;
    }

    public static AuthorizationResponse createAuthorizationResponse( AuthoritiesEnum role, String token, String name){
        return new AuthorizationResponse(role , token , name);
    }
    private void setGrantedAuth(AuthoritiesEnum role) {
        this.grantedAuth = List.of(new SimpleGrantedAuthority(role.getRole()));
    }
}
