package com.my.user;

import com.my.config.jwt.JwtProvider;
import com.my.user.exception.UserErrorCode;
import com.my.user.exception.UserException;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void userJoin(UserJoinDto userJoinDto) {
        if (userExist(userJoinDto.id())) {
            throw new UserException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        userRepository.save(userJoinDto.getUser(passwordEncoder));
    }

    private boolean userExist(String id) {
        return userRepository.existsUserById(id);
    }


    private boolean userPasswordCheck(String pw, String encodePw) {
        return passwordEncoder.matches(pw, encodePw);
    }

    public String userLogin(UserLoginDto userLoginDto) {
        try {
            User user = userRepository.findUserById(userLoginDto.id())
                    .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
            if (!userPasswordCheck(userLoginDto.pw(), user.getPw())) {
                throw new UserException(UserErrorCode.USER_NOT_FOUND);
            }
            return jwtProvider.createToken(user);
        } catch (Exception e) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }


    }
}
