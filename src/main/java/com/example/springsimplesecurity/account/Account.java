package com.example.springsimplesecurity.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private Integer id;
    private String email;
    private String password;
}
