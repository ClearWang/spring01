package com.will.bean;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class Student {
    private String username = "Jack";

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
