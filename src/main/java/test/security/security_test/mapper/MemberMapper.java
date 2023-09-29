package test.security.security_test.mapper;

import org.springframework.stereotype.Repository;
import test.security.security_test.dto.signup.MemberSignUpRequest;

import java.util.Optional;

@Repository
public interface MemberMapper {

    Optional<Integer> findByIdCount(String id);
    int userSave(MemberSignUpRequest memberSignUpRequest);
}
