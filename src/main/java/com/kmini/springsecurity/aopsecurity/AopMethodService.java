package com.kmini.springsecurity.aopsecurity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AopMethodService {


    @PreAuthorize("hasRole('ROLE_USER')")
    public void methodSecured() {
        System.out.println("methodSecured");
    }


}
