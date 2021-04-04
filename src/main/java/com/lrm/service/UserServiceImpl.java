package com.lrm.service;

import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 邹明
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }

    @Override
    public User saveUser(String username, String nickname, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(MD5Utils.code(password));
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
