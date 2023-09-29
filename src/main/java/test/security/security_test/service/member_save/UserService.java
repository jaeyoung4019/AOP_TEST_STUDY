package test.security.security_test.service.member_save;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.security.security_test.dto.signup.MemberSignUpRequest;
import test.security.security_test.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class UserService implements MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public int userSave(MemberSignUpRequest memberSignUpRequest) {
        memberMapper.findByIdCount(memberSignUpRequest.getId()).filter(
                count -> count == 0).orElseThrow(() -> {
            throw new IllegalStateException("존재하는 회원입니다.");
        });
        String password = memberSignUpRequest.getPassword();
        memberSignUpRequest.setPassword(bCryptPasswordEncoder.encode(password));
        return memberMapper.userSave(memberSignUpRequest);
    }
}