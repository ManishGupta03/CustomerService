package com.example.p3.controller;

import com.example.p3.entity.Customer;
import com.example.p3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        return new ResponseEntity("Customer save successfully", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity getCustomer(@RequestParam int id){
        Customer customer=customerService.getCustomer(id);
        if(customer==null) return new ResponseEntity("customer not found",HttpStatus.NOT_FOUND);
        return new ResponseEntity(customer,HttpStatus.FOUND);
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Customer customer){
        Customer newCustomer = customerService.update(customer);
        if(customer ==null) return new ResponseEntity("Customer Not Found",HttpStatus.NOT_FOUND);
        return new ResponseEntity(newCustomer,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam int id){
        String customer = customerService.delete(id);
        if(customer ==null) return new ResponseEntity("Customer Not Found",HttpStatus.NOT_FOUND);
        return new ResponseEntity(customer,HttpStatus.OK);
    }

    //pagenation
    @GetMapping("/getAll")
    public ResponseEntity<Page<Customer>> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Customer> customers = customerService.findAll(pageable);
        return ResponseEntity.ok(customers);
    }

}

