package com.my.config.jwt;

import com.my.aop.LogClass;
import com.my.user.vo.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@LogClass
public class JwtProvider {

    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        key = createSignature(secret);
        this.tokenValidityInMilliseconds = 1000 * 60 * 60 * 24;
    }

    public String createToken(final User user) {


        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(String.valueOf(user.getName()))
                .setClaims(createClaims(user)) // 정보 저장
                .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .setExpiration(validity)
                .compact();
    }

    public boolean isValidToken(String token) {
        if (token == null || token.isBlank())
            return false;
        try {
            Claims claims = getClaimsFormToken(token);
            if (null != claims) {
                return true;
            }
            return false;
        } catch (ExpiredJwtException exception) {
            throw new JwtException("토큰 만료");
        } catch (JwtException exception) {
            throw new JwtException("Token Tampered");
        } catch (NullPointerException exception) {
            throw new JwtException("토큰이 존재하지 않습니다.");
        }
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Key createSignature(final String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, Object> createClaims(final User user) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();
        claims.put("userNo", user.getNo());
        claims.put("userName" , user.getName());
        claims.put("userType" , user.getType());
        return claims;
    }

    private Claims getClaimsFormToken(final String token) {

        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token).getBody();
    }

    public User getTokenConvertUser (final String token) { // 토큰을 유저 객체로 바꾸어준다.
        Claims claims = getClaimsFormToken(token);
        String type = claims.get("userType").toString();
        Long no = Long.parseLong(claims.get("userNo").toString());
        String name = claims.get("userName").toString();
        User member = User
                .builder()
                .no(no)
                .name(name)
                .type(type)
                .build();
        return member;
    }
}
