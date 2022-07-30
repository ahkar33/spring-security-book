package com.maw.crudsecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "asdfqwer";
        System.out.println(encoder.encode(rawPassword));
    }    
}
