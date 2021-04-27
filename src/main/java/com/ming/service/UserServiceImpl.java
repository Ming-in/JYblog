package com.ming.service;

import com.ming.dao.UserRepository;
import com.ming.po.User;
import com.ming.util.MD5Utils;
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
    public User saveUser(String username, String password, String nickname, String email) {
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

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
