package test.security.security_test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.security.security_test.dto.authorization.AuthorizationRequest;
import test.security.security_test.dto.authorization.AuthorizationResponse;
import test.security.security_test.mapper.AuthorizationMapper;
import test.security.security_test.utill.auth.JwtUtil;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationMapper authorizationMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    public AuthorizationResponse authorizationMember(AuthorizationRequest authorizationRequest){
        return authorizationMapper.authorizationMember(authorizationRequest)
                .filter(authorizationResponse ->
                    bCryptPasswordEncoder.matches(authorizationRequest.getPassword() , authorizationResponse.getPassword()))
                .map( authorizationResponse -> {
                    String token = jwtUtil.generateToken( authorizationResponse.getIdx() , authorizationResponse.getRole());
                    return AuthorizationResponse.createAuthorizationResponse(authorizationResponse.getRole() , token , authorizationResponse.getName());
                })
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    public Map<String , ?> memberAuthorizationFindById (Long idx){
        return authorizationMapper.memberAuthorizationFindById(idx)
                .orElseThrow(() -> {throw new UsernameNotFoundException("존재하지 않는 회원입니다.");});
    }
}
