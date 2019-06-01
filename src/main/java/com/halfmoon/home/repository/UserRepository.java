package com.halfmoon.home.repository;

import com.halfmoon.home.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: xuzhangwang
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findByName(String userName);

}
