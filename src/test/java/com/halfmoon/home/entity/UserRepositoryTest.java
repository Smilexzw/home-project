package com.halfmoon.home.entity;

import com.halfmoon.home.ApplicationTests;
import com.halfmoon.home.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: xuzhangwang
 */
public class UserRepositoryTest extends ApplicationTests{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne() {
        User user = userRepository.findOne(1L);
        System.out.println(user.toString());
    }

}
