package com.my.user;

import com.my.user.vo.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void userJoin(UserJoinDto userJoinDto){
        if(userExist(userJoinDto.getId())){
            throw new IllegalStateException("중복된 계정입니다.");
        };



        userRepository.save(userJoinDto.getUser());
    }

    private boolean userExist(String id){
        return userRepository.existsUserById(id);
    }

}
