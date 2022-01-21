package com.ming.web;

import com.ming.dao.TestRepository;
import com.ming.po.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestRepository testRepository;

    @RequestMapping
    public String test(@RequestParam("test") String str) {
        logger.info("测试成功");
        Test test = testRepository.findByName("test");
        return "{res: \"测试成功\",param:\"" + str + "\" }\n" + test.toString();
    }

    @PostMapping("/update")
    public String updateUser(Test test) {
        if (test.getId() != null) {
            Test t = testRepository.findById(test.getId());
            if (t != null) {
                BeanUtils.copyProperties(t, test);
                t = testRepository.save(t);
            } else {
                t = testRepository.saveAndFlush(test);
            }
            return t.toString();
        }

        Test t = testRepository.saveAndFlush(test);
        return t.toString();
    }

}
