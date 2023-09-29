package test.security.security_test.utill.aop.test_member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메소드 타겟
@Retention(RetentionPolicy.RUNTIME) // 실행 시 인식
public @interface MethodAop {
    String value();
}
