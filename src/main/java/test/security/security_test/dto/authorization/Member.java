package test.security.security_test.dto.authorization;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Getter
@ToString
public class Member implements UserDetails {

    private final long idx;
    private final String username;
    private String password;
    private final List<GrantedAuthority> authorities;
    private final boolean enabled;


    private Member(long idx, String username, String password, List<GrantedAuthority> authorities, boolean enabled) {
        this.idx = idx;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @Builder
    private Member(long idx, String username, List<GrantedAuthority> authorities, boolean enabled) {
        this.idx = idx;
        this.username = username;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public static Member memberAuthorizationFindByIdVo(Map<String , ?> authorizationMemberInfo) {
        return Member.builder()
                .username(authorizationMemberInfo.get("id").toString())
                .idx((Integer) authorizationMemberInfo.get("idx"))
                .enabled((boolean) authorizationMemberInfo.get("enabled"))
                .authorities(List.of(new SimpleGrantedAuthority( AuthoritiesEnum.findByCodeEnum( authorizationMemberInfo.get("role").toString()).getRole()  )))
                .build();

//        Member member = new Member();
//        Class<?> classMember = Class.forName("test.security.security_test.dto.authorization.Member");
//        Field[] declaredFields = classMember.getDeclaredFields();
//
//        for (Field declaredField : declaredFields) {
//            String[] split = declaredField.toString().split("\\.");
//            String memberFieldName = split[split.length - 1];
//            for (String key : strings) {
//                if(memberFieldName.equals(key)){
//                    Method[] declaredMethods = classMember.getDeclaredMethods();
//                    Method setIdx = classMember.getDeclaredMethod("setIdx", long.class);
//                    setIdx.invoke( member , authorizationMemberInfo.get(key));
//                    System.out.println("member.toString() = " + member.toString());
//                    for (Method declaredMethod : declaredMethods) {
//                        System.out.println("declaredMethod.getName() = " + declaredMethod.getName());
//                    }
//                    System.out.println("key = " + key);
//                }
//            }
//        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
