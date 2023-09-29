package test.security.security_test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.security.security_test.dto.Response;
import test.security.security_test.dto.authorization.AuthorizationResponse;
import test.security.security_test.dto.signup.MemberSignUpRequest;
import test.security.security_test.service.member_save.MemberService;
import test.security.security_test.utill.exception.RestException;

import javax.validation.Valid;

@RestController
@RequestMapping("/save")
public class MemberSaveController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberSaveController(@Qualifier(value = "userService") MemberService memberService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/user/signup")
    public Response<Integer> userSave(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest
                                    , BindingResult bindingResult) throws RestException {
        if(bindingResult.hasErrors()) {
            throw new RestException(404 , "알맞은 값을 입력해 주세요.");
        }
        int result = memberService.userSave(memberSignUpRequest) >= 1 ? 1 : 0;
        return new Response.ResponseBuilder<Integer>(result == 1 ? "OK" : "FAIL" , result == 1 ? 200 : 500)
                .resultResponse(result)
                .total(result)
                .build();
    }
}
