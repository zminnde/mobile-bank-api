package com.seb.mobilebankapi.security;

import com.seb.mobilebankapi.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.seb.mobilebankapi.util.DataAccessUtils.getEntity;

@Service
public final class SampleUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public SampleUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var customer = getEntity(customerRepository::findByUserName, username);
        return User.withDefaultPasswordEncoder()
                .username(customer.getUserName())
                .password(customer.getPassword())
                .roles("USER")
                .build();
    }

}
