package com.kmini.springsecurity.repository;

import com.kmini.springsecurity.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
    int countByUsername(String username);
}
