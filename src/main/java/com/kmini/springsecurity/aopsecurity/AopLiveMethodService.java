package com.kmini.springsecurity.aopsecurity;

import org.springframework.stereotype.Service;

@Service
public class AopLiveMethodService {

    public void liveMethodSecured() {
        System.out.println("liveMethodSecured");
    }

    public void liveMethodNoSecured() {
        System.out.println("liveMethodNoSecured");
    }
}
