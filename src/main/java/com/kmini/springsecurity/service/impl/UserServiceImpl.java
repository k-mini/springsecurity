package com.kmini.springsecurity.service.impl;

import com.kmini.springsecurity.domain.Account;
import com.kmini.springsecurity.repository.UserRepository;
import com.kmini.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {

        userRepository.save(account);

    }
}
