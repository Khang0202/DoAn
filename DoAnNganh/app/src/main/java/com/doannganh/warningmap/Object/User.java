package com.doannganh.warningmap.Object;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String sex;
    private int phone;
}
