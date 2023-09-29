package test.security.security_test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/test")
public class AuthTestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
