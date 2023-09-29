package test.security.security_test.aop_test.pointcut;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import test.security.security_test.utill.aop.test_member.AopMemberServiceImpl;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = AopMemberServiceImpl.class.getMethod("hello", String.class);
    }


    @Test
    void printMethod(){
        //16:50:14.093 [main] INFO test.security.security_test.aop_test.pointcut.ExecutionTest - test Method = public java.lang.String test.security.security_test.utill.aop.test_member.AopMemberServiceImpl.hello(java.lang.String)
        log.info("test Method = {}" , helloMethod);
    }

    @Test
    void exactMatch(){
        //public java.lang.String test.security.security_test.utill.aop.test_member.AopMemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String test.security.security_test.utill.aop.test_member.AopMemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, AopMemberServiceImpl.class)).isTrue();

    }

    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void patternMatch(){
        pointcut.setExpression("execution(* hel*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void patternMatch2(){
        pointcut.setExpression("execution(* *el*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }


    @Test
    void packageExecutionMatch(){
        pointcut.setExpression("execution(* test.security.security_test.utill.aop..*.*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExecutionMatch(){  // 부모타입
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExecutionMatch2() throws NoSuchMethodException {  // 부모타입
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Method internal = AopMemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internal , AopMemberServiceImpl.class)).isFalse();
    }


    @Test
    void typeExecutionMatch3() throws NoSuchMethodException {  // 부모타입
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberServiceImpl.*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Method internal = AopMemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internal , AopMemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeStringExecutionMatch(){  //파라미터
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(String))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeStringExecutionMatch2(){  //파라미터
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*())");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeStringExecutionMatch3(){  //파라미터
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(*))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeStringExecutionMatch4(){  //파라미터
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeStringExecutionMatch5(){  //파라미터
        pointcut.setExpression("execution(* test.security.security_test.utill.aop.test_member.AopMemberService.*(String , ..))");  // .. 파라미터 -> 파라미터 타입 , 수가 상관이 없다.
        Assertions.assertThat(pointcut.matches(helloMethod , AopMemberServiceImpl.class)).isTrue();
    }
}
