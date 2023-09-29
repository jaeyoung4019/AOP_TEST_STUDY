package test.security.security_test.service.member_save;


import test.security.security_test.dto.signup.MemberSignUpRequest;

public interface MemberService {

    int userSave(MemberSignUpRequest memberSignUpRequest);
}
