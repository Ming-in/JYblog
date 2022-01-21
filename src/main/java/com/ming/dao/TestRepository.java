package com.ming.dao;

import com.ming.po.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByName(String name);

    Test findById(Long id);

}
