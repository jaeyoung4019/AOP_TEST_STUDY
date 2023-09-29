package test.security.security_test.utill.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    // 클래스 이름 패턴이 service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("execution(* test.security.security_test.mapper..*(..))")
    public void allMapper(){}

    @Pointcut("allService() || allMapper()")
    public void serviceOrMapper(){}


}
