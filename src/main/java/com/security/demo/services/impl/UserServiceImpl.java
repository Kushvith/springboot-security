package com.security.demo.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.demo.repository.UserRespository;
import com.security.demo.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRespository userRespository;
    
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String username){
                return userRespository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
            }
        };
    }
}
