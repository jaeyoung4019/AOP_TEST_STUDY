package test.security.security_test.utill.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5 {

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("test.security.security_test.utill.aop.PointCuts.serviceOrMapper()") //포인트 컷 test.security.security_test.service.member_save 하위 패키지 .. 다 포함
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{  // 어드바이스
            log.info(" log = {}" , joinPoint.getSignature()); // 조인포인트 시그니쳐 메소드 정보  t.s.security_test.utill.aop.AspectV1     :  log = int test.security.security_test.service.member_save.UserService.userSave(MemberSignUpRequest)
            return  joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TranAspect {
        @Around("test.security.security_test.utill.aop.PointCuts.serviceOrMapper()")
        // 특정 패키지 하위 패키지 이면서 클래스 이름 패턴이 service 인 것
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try{
                log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
                Object proceed = joinPoint.proceed();
                log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
                return proceed;
            } catch (Exception e){
                log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
                throw new Exception(e.getMessage());
            } finally {
                log.info(" <리소스 릴리즈< {}" , joinPoint.getSignature());
            }
        }
    }

}
