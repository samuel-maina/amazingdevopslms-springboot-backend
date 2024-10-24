package com.AmazingDevOpsLMS.security;

import com.AmazingDevOpsLMS.model.User;
import com.AmazingDevOpsLMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
System.out.println("##############################");
        User user = userRepository.getUserByEmail(userId).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        return UserDetailsImpl.build(user);
    }
}

