package com.halfmoon.home.service.user;

import com.halfmoon.home.entity.User;
import com.halfmoon.home.repository.UserRepository;
import com.halfmoon.home.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xuzhangwang
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(long id) {
        return userRepository.findOne(id);
    }
}
