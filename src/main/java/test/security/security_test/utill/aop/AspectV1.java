package test.security.security_test.utill.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* test.security.security_test.service.member_save..*(..))") //포인트 컷 test.security.security_test.service.member_save 하위 패키지 .. 다 포함
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{  // 어드바이스
        log.info(" log = {}" , joinPoint.getSignature()); // 조인포인트 시그니쳐 메소드 정보  t.s.security_test.utill.aop.AspectV1     :  log = int test.security.security_test.service.member_save.UserService.userSave(MemberSignUpRequest)
        return  joinPoint.proceed();
    }
}
