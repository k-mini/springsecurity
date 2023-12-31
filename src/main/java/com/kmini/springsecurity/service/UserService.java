package com.kmini.springsecurity.service;

import com.kmini.springsecurity.domain.dto.AccountDto;
import com.kmini.springsecurity.domain.entity.Account;

import java.util.List;

public interface UserService {

    void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);

    void order();

}
