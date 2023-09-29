package test.security.security_test.utill.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import test.security.security_test.dto.authorization.Member;
import test.security.security_test.dto.authorization.enums.AuthoritiesEnum;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String SPRING_JWT_SECRET;

    public Member getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            return null;
        }
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String generateToken(long idx, AuthoritiesEnum role) {
        return Jwts.builder()
                .claim("idx", idx)
                .claim("ROLE", role.getRole())
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(getKey())
                .compact();
    }

    private Date getExpiration() {
        return Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SPRING_JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            //  토큰 만료
            final Date expiration = getClaims(token).getExpiration();
            return !expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getMemberIdx(String token) {
        Claims claims = getClaims(token);
        return claims.get("idx", Long.class);
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("ROLE", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
