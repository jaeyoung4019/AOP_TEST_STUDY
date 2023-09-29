package test.security.security_test.utill.aop.test_member;

import org.springframework.stereotype.Component;
import test.security.security_test.utill.aop.test_member.annotation.ClassAop;
import test.security.security_test.utill.aop.test_member.annotation.MethodAop;

@ClassAop
@Component
public class AopMemberServiceImpl implements AopMemberService{

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
