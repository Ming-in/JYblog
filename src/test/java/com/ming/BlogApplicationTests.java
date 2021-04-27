package com.ming;

import com.ming.dao.UserRepository;
import com.ming.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setUsername("123");
        user.setNickname("123");
        user.setPassword("123");
//        userRepository.save(user);
    }

}