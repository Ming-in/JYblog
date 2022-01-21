package com.ming.web;

import com.ming.dao.UserRepository;
import com.ming.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @RequestMapping
    public String test(@Param("test") String test) {
        logger.info("测试成功");
        User user = userRepository.findByUsername("test2");
        return "{res: \"测试成功\",param:\"" + test + "\" }\n" + user.toString();
    }
}
