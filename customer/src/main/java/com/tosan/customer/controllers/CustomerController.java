package com.tosan.customer.controllers;

import com.tosan.customer.services.CustomerService;
import com.tosan.customer.data.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path= "customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping(path = "/define")
    public String defineCustomer(@RequestParam("name") String name,
                               @RequestParam("nationalCode") Long nationalCode,
                               @RequestParam("birthDate") String birthDate,
                               @RequestParam("mobileNumber") String mobileNumber,
                               @RequestParam("customerType") int customerType) {
        return customerService.defineCustomer(name, nationalCode, birthDate, mobileNumber, customerType);
    }

    @GetMapping(path = "/get-all")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping(path= "/accounts")
    public List getDepositsOfCustomer(@RequestParam(value = "nationalCode") Long nationalCode){
        return customerService.getDepositsOfCustomer(nationalCode);
    }

    @DeleteMapping(path = "/delete")
    public void deleteCustomer(@RequestParam(value = "nationalCode") Long nationalCode){
        customerService.deleteCustomer(nationalCode);
    }

    @PostMapping(path = "/change-status")
    public void changeCustomerStatus(@RequestParam(value = "nationalCode") Long nationalCode){
        customerService.changeCustomerStatus(nationalCode);
    }

    @GetMapping(path = "/get-customer")
    public Object getCustomer(@RequestParam("customerId") Long customerId){
        return customerService.getCustomer(customerId);
    }
}
