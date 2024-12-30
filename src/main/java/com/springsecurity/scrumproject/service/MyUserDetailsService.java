package com.springsecurity.scrumproject.service;

import com.springsecurity.scrumproject.model.UserPrincipal;
import com.springsecurity.scrumproject.model.Users;
import com.springsecurity.scrumproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=userRepo.findByUsername(username);
        if(users==null)
        {
            System.out.println("User Not Found..!");
            throw new UsernameNotFoundException("User Not Found..!");
        }
        return new UserPrincipal(users);
    }
}
