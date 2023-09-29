package test.security.security_test.aop_test;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import test.security.security_test.dto.signup.MemberSignUpRequest;
import test.security.security_test.mapper.MemberMapper;
import test.security.security_test.service.member_save.MemberService;
import test.security.security_test.utill.aop.*;

@Slf4j
@SpringBootTest
@Import({AspectV6Advice.class}) // 스프링 빈으로 등록 , 설정파일 넣을 때
public class AopTest {

    @Autowired
    @Qualifier(value = "userService")
    MemberService memberService;

    @Autowired
    MemberMapper memberMapper;


    @Test
    public void aopInfo(){
        log.info(" aop 가 적용이 되었는가 memberService  = {}" , AopUtils.isAopProxy(memberService));
        log.info(" aop 가 적용이 되었는가 memberMapper   = {}" , AopUtils.isAopProxy(memberMapper));
    }

    @Test
    @Rollback(value = true)
    public void success(){
        memberService.userSave(new MemberSignUpRequest("testId6" , "1234" , "1234"));
    }
}
