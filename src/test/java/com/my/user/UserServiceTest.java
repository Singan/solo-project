package com.my.user;

import com.my.config.jwt.JwtProvider;
import com.my.user.UserService;
import com.my.user.exception.UserException;
import com.my.user.exception.UserErrorCode;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import com.my.user.vo.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserService userService;


    String id = "test_id";
    String pw = "password";
    private User createUser() {
        return User.builder()
                .no(1L)
                .id(id)
                .pw(passwordEncoder.encode(pw))
                .name("test_name")
                .build();
    }

    @Nested
    @DisplayName("회원가입")
    class UserJoin {
        @Test
        @DisplayName("성공 케이스")
        public void testUserJoinSuccess() {
            UserJoinDto userJoinDto = new UserJoinDto(id, pw, "test_name","1");

            when(userRepository.existsUserById(userJoinDto.id())).thenReturn(false);
            when(passwordEncoder.encode(userJoinDto.pw())).thenReturn("encoded_password");

            assertDoesNotThrow(() -> userService.userJoin(userJoinDto));

            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("실패 케이스 - 중복된 계정")
        public void testUserJoinDuplicateId() {
            UserJoinDto userJoinDto = new UserJoinDto(id, pw, "test_name","1");

            when(userRepository.existsUserById(userJoinDto.id())).thenReturn(true);

            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                userService.userJoin(userJoinDto);
            });

            assertEquals("중복된 계정입니다.", exception.getMessage());
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("로그인")
    class UserLogin {
        @Test
        @DisplayName("성공 케이스")
        public void testUserLoginSuccess() {
            UserLoginDto userLoginDto = new UserLoginDto(id, pw);
            User user = createUser();

            when(userRepository.findUserById(userLoginDto.id())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(userLoginDto.pw(), user.getPw())).thenReturn(true);
            when(jwtProvider.createToken(user)).thenReturn("jwt_token");

            String token = userService.userLogin(userLoginDto);

            assertNotNull(token);
            assertEquals("jwt_token", token);
        }

        @Test
        @DisplayName("실패 케이스 - 사용자 없음")
        public void testUserLoginUserNotFound() {
            UserLoginDto userLoginDto = new UserLoginDto(id, pw);

            when(userRepository.findUserById(userLoginDto.id())).thenReturn(Optional.empty());

            UserException exception = assertThrows(UserException.class, () -> {
                userService.userLogin(userLoginDto);
            });

            assertEquals(UserErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("실패 케이스 - 비밀번호 불일치")
        public void testUserLoginPasswordMismatch() {
            UserLoginDto userLoginDto = new UserLoginDto(id, pw+1);
            User user = createUser();

            when(userRepository.findUserById(userLoginDto.id())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(userLoginDto.pw(), user.getPw())).thenReturn(false);

            UserException exception = assertThrows(UserException.class, () -> {
                userService.userLogin(userLoginDto);
            });

            assertEquals(UserErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        }
    }
}