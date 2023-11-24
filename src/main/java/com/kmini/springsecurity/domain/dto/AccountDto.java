package com.kmini.springsecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  AccountDto {

    private String id;
    private String username;
    private String email;
    private String age;
    private String password;
    private List<String> roles;
}
