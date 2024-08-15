package com.my.user;

import com.my.user.vo.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User , Long> {

    boolean existsUserById(String id);

    @EntityGraph(attributePaths = {"userRoles"})
    Optional<User> findUserById(String id);
}
