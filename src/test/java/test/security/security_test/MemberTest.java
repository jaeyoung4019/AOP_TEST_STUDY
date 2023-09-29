package test.security.security_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.security.security_test.dto.authorization.Member;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;
import test.security.security_test.service.AuthorizationService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class MemberTest {
    
    @Autowired
    private AuthorizationService authorizationService;
    @Test
    public void getDisCountTest(){
        AuthoritiesEnum user_001 = AuthoritiesEnum.findByCodeEnum("USER_002");
        System.out.println("user_001 = " + user_001);

        List<AuthoritiesEnum> collect = Arrays.stream(AuthoritiesEnum.values()).filter(x -> x.getCode().equals("USER_002")).collect(Collectors.toList());
        for (AuthoritiesEnum authoritiesEnum : collect) {
            System.out.println("authoritiesEnum = " + authoritiesEnum);
        }

        Function<Long, Long> discount = user_001.getDiscount();
        Long apply = discount.apply(30000L);
        System.out.println("apply = " + apply);

        Long user_003 = AuthoritiesEnum.findByCodeGetDiscount("USER_002", 40000L);
        System.out.println("user_003 = " + user_003);
    }
    
    @Test
    public void mybatisMapTest()  {
        Map<String, ?> test = authorizationService.memberAuthorizationFindById(1L);

        System.out.println("test = " + test);
        Set<String> keys = test.keySet();

        for (String key : keys) {
            System.out.println("key = " + key);
            System.out.println("test.get(key) = " + test.get(key));
        }

        Member member = Member.memberAuthorizationFindByIdVo(test);
        System.out.println("member = " + member.toString());
    }
}
