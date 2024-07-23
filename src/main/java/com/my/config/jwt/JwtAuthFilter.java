package com.my.config.jwt;

import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthenticationEntryPoint entryPoint;

    public Authentication getAuthentication(User user) {
        UserDetailsDto userDetails = new UserDetailsDto(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("X-AUTH-TOKEN");
        try {
            if (token != null && !token.isBlank()) {
                if (jwtProvider.isValidToken(token)) {
                    User user = jwtProvider.getTokenConvertUser(token);
                    if (user != null) {
                        SecurityContextHolder.getContext().setAuthentication(getAuthentication(user));
                    }
                }
            }

        }catch (JwtException e){

            //entryPoint.commence(request, response, new AuthenticationException(e.getMessage()) {});
        }
        chain.doFilter(request, response);


    }
}
