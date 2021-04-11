package com.lrm;


import com.lrm.po.User;
import org.junit.Test;

/**
 * @author Ming
 * 创建时间：2021/4/5 0:04
 */
public class MyTest {

    @Test
    public void test(){
        final User user = new User();
        user.setUsername("Ming");
        System.out.println(user);
        modify(user);
        System.out.println(user);
    }

    private void modify(final User user){
        user.setPassword("123");
    }
}
