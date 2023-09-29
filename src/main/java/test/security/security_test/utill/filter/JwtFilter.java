package test.security.security_test.utill.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import test.security.security_test.dto.authorization.Member;
import test.security.security_test.dto.authorization.enums.ErrorCode;
import test.security.security_test.service.AuthorizationService;
import test.security.security_test.utill.auth.JwtUtil;
import test.security.security_test.utill.matcher.URLMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.header}")
    private String SPRING_JWT_HEADER;

    private JwtUtil jwtUtil;

    private AuthorizationService authorizationService;

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        URLMatcher saveUrlMatcher = new URLMatcher(List.of("/save/user/signup"));
        if(saveUrlMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        URLMatcher loginUrlMatcher = new URLMatcher(List.of("/auth/user/login"));
        if(loginUrlMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String TOKEN = request.getHeader(SPRING_JWT_HEADER);
        URLMatcher anyUrlMatcher = new URLMatcher(List.of("/auth/test/**"));
        if(anyUrlMatcher.matches(request)){

            if(TOKEN == null){;
                error(ErrorCode.UNAUTHORIZED);
                return;
            }

            try {
                if (!jwtUtil.validateToken(TOKEN)) {
                    error(ErrorCode.EXPIRED_TOKEN);
                    return;
                }
            } catch (Exception e) {
                error(ErrorCode.INVALID_TOKEN);
                return;
            }

            if (TOKEN != null){
                Map<String, ?> resultMap = authorizationService.memberAuthorizationFindById(jwtUtil.getMemberIdx(TOKEN));
                if(resultMap.isEmpty()){
                    error(ErrorCode.NOT_FOUND_USER);
                    return;
                }
                Member member = Member.memberAuthorizationFindByIdVo(resultMap);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( member, null,  member.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private void error(ErrorCode code) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();


        response.sendRedirect(request.getContextPath() + "/error/" + code.getValue());
    }
}
