package com.example.Translation.Management.Service.Servc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Translation.Management.Service.Entity.AppUserEntity;
import com.example.Translation.Management.Service.Repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { AppUserEntity user =
	 * userRepository.findByUsername(username) .orElseThrow(new
	 * UsernameNotFoundException("User not found: " + username));
	 * 
	 * // Here we map a simple role to authorities; extend as needed
	 * SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +
	 * user.getRole()); return new
	 * org.springframework.security.core.userdetails.User(user.getUsername(),
	 * user.getPassword(), Arrays.asList(authority)); }
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity user = userRepository.findByUsername(username)
                     .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Map role to authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Arrays.asList(authority)
        );
    }

}