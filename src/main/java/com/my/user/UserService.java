package com.my.user;

import com.my.aop.LogClass;
import com.my.config.jwt.JwtProvider;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@LogClass
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void userJoin(UserJoinDto userJoinDto) throws Exception {
        if (userExist(userJoinDto.id())) {
            throw new Exception("중복된 계정입니다.");
        }
        ;


        userRepository.save(userJoinDto.getUser(passwordEncoder));
    }

    private boolean userExist(String id) {
        return userRepository.existsUserById(id);
    }

    public String userLogin(UserLoginDto userLoginDto) throws RuntimeException {
        User user = userRepository.findUserById(userLoginDto.id()).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));

        if (!passwordEncoder.matches(userLoginDto.pw(), user.getPw())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(user);

    }
}
