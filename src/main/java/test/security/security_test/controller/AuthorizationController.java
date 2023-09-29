package test.security.security_test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import test.security.security_test.dto.Response;
import test.security.security_test.dto.authorization.AuthorizationRequest;
import test.security.security_test.dto.authorization.AuthorizationResponse;
import test.security.security_test.service.AuthorizationService;
import test.security.security_test.utill.exception.RestException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/user/login")
    public Response<AuthorizationResponse> authorizationMember
            (@RequestBody @Valid AuthorizationRequest authorizationRequest , BindingResult bindingResult) throws RestException {

            if(bindingResult.hasErrors()) {
                throw new RestException(404 , "존재하지 않는 회원입니다.");
            }

            return new Response.ResponseBuilder<AuthorizationResponse>("OK" , 200)
                    .resultResponse(authorizationService.authorizationMember(authorizationRequest))
                    .total(1)
                    .build();
    }




}
