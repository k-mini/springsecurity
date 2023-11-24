package com.kmini.springsecurity.repository;

import com.kmini.springsecurity.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {


    Account findByUsername(String username);
}
