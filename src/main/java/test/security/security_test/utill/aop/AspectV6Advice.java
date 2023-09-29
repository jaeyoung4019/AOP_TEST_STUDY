package test.security.security_test.utill.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {
//    @Around("test.security.security_test.utill.aop.PointCuts.serviceOrMapper()")
//    // 특정 패키지 하위 패키지 이면서 클래스 이름 패턴이 service 인 것
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try{
//            //@Before
//            log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
//            Object proceed = joinPoint.proceed();
//            //@Afterreturning
//            log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
//            return proceed;
//        } catch (Exception e){
//            //@AfterThrowing
//            log.info(" <트랜잭션 시작> {}" , joinPoint.getSignature());
//            throw new Exception(e.getMessage());
//        } finally {
//            //@After
//            log.info(" <리소스 릴리즈< {}" , joinPoint.getSignature());
//        }
//    }

//    @Before("test.security.security_test.utill.aop.PointCuts.serviceOrMapper()")
//    public void doBefore(JoinPoint joinPoint){
//        log.info(" before = {}" , joinPoint.getSignature());
//    }

    @AfterReturning(value = "test.security.security_test.utill.aop.PointCuts.serviceOrMapper()" , returning = "result")
    public void doAfterReturning(JoinPoint joinPoint , Object result) {
        log.info(" return = {} {} " , joinPoint.getSignature() , result);
    }

    @AfterThrowing(value = "test.security.security_test.utill.aop.PointCuts.serviceOrMapper()" , throwing = "result")
    public void doAfterThrowing(JoinPoint joinPoint , Exception result) {
        log.info(" return = {} {} " , joinPoint.getSignature() , result);
    }

    @After(value = "test.security.security_test.utill.aop.PointCuts.serviceOrMapper()" )
    public void doAfter(JoinPoint joinPoint){
        log.info("after =  {} " , joinPoint.getSignature());
    }
}
