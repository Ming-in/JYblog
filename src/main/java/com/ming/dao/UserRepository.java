package com.ming.dao;

import com.ming.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author 邹明
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
