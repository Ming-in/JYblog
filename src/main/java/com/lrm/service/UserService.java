package com.lrm.service;

import com.lrm.po.User;

/**
 * @author 邹明
 */
public interface UserService {

    User checkUser(String username, String password);

    User saveUser(String username,String nickname, String password,String email);

    User findByUsername(String username);
}
