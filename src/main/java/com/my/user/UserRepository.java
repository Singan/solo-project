package com.my.user;

import com.my.user.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User , Long> {

    boolean existsUserById(String id);


    User findUserById(String id);
}
