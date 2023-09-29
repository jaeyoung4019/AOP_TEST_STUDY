package test.security.security_test.mapper;

import org.springframework.stereotype.Repository;
import test.security.security_test.dto.authorization.AuthorizationRequest;
import test.security.security_test.dto.authorization.AuthorizationResponse;

import java.util.Map;
import java.util.Optional;

@Repository
public interface AuthorizationMapper {
    Optional<AuthorizationResponse> authorizationMember(AuthorizationRequest authorizationRequest);
    Optional<Map<String , ?>> memberAuthorizationFindById(Long idx);
}
