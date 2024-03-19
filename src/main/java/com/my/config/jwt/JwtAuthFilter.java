package com.my.config.jwt;

import com.my.aop.LogClass;
import com.my.user.UserService;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@LogClass
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    public Authentication getAuthentication(User user) {
        UserDetailsDto userDetails = new UserDetailsDto(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("X-AUTH-TOKEN");

        if (jwtProvider.isValidToken(token)) {
            User user = jwtProvider.getTokenConvertUser(token);
            if (user != null) {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(user));
            }


        }

        chain.doFilter(request, response);

    }


}
