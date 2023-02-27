package com.seb.mobilebankapi.security;

import com.seb.mobilebankapi.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public record SampleUserDetailsService(
        CustomerRepository customerRepository
) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var customer = customerRepository.findByUserName(username);
        if (customer == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        return User.withDefaultPasswordEncoder()
                .username(customer.getUserName())
                .password(customer.getPassword())
                .roles("USER")
                .build();
    }

}
