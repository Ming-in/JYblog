package com.ming.service;

import com.ming.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 邹明
 */
public interface UserService {

    User checkUser(String username, String password);

    User saveUser(String username,String password,String nickname,String email);

    User findByUsername(String username);

    User findById(Long id);

    User save(User user);

    Page<User> listUser(Pageable pageable);

    void deleteUser(Long id);
}
