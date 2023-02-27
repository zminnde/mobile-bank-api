package com.seb.mobilebankapi.repository;

import com.seb.mobilebankapi.model.entity.Customer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

    Customer findByUserName(String userName);
}
