package com.example.p3.service;

import com.example.p3.entity.Customer;
import com.example.p3.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }


    public Customer update(Customer customer) {
        if(customerRepo.findById(customer.getCustomerId())==null) return null;
        return customerRepo.save(customer);
    }

    public String delete(int id) {
        Customer customer=customerRepo.findById(id);
        if (customer==null) return null;
        customerRepo.delete(customer);
        return "Delete Successfully";
    }

    public Customer getCustomer(int id) {
        Customer customer=customerRepo.findById(id);
        if (customer==null) return null;
        return customer;
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepo.findAll(pageable);
    }

}
