package test.security.security_test.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Alias("memberSignUpRequest")
@AllArgsConstructor
public class MemberSignUpRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]*$" , message = "영어 , 숫자 조합으로 아이디를 입력해주세요.")
    private String id;
    @NotNull
    private String password;
    private String name;

}
