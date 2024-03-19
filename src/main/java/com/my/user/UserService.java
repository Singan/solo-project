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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@LogClass
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Transactional
    public void userJoin(UserJoinDto userJoinDto){
        if(userExist(userJoinDto.getId())){
            throw new IllegalStateException("중복된 계정입니다.");
        };
        userJoinDto.setPw(passwordEncoder.encode(userJoinDto.getPw()));


        userRepository.save(userJoinDto.getUser());
    }

    private boolean userExist(String id){
        return userRepository.existsUserById(id);
    }
    public String userLogin(UserLoginDto userLoginDto){
        return jwtProvider.createToken(userRepository.findUserById(userLoginDto.getId()));

    }
}
