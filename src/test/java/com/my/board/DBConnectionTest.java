package com.my.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DBConnectionTest {
    @Autowired
    EntityManager em;

    @Test
    public void test(){
        Assertions.assertTrue(em.isOpen());
    }
}
