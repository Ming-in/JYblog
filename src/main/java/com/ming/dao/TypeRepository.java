package com.ming.dao;

import com.ming.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ming
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    Type findById(Long id);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
