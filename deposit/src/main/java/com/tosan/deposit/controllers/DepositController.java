package com.tosan.deposit.controllers;

import com.tosan.deposit.data.Deposit;
import com.tosan.deposit.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("deposit")
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService){
        this.depositService = depositService;
    }

    @PostMapping(path = "/open")
    public void openDeposit(@RequestParam(value = "customerId") Long customerId,
                            @RequestParam(value = "depositType") int depositType,
                            @RequestParam(value = "fName") String fName,
                            @RequestParam(value = "lName") String lName,
                            @RequestParam(value = "initBalance") Long initBalance){
        depositService.openDeposit(customerId, depositType, fName, lName, initBalance);
    }

    @GetMapping(path = "/get-all")
    public List<Deposit> getAllDeposits(){
        return depositService.getAllDeposits();
    }

    @PostMapping(path = "/get-customer")
    public Object getAllCustomersByDepositNumber(@RequestParam(value = "depositNumber") Long depositNumber){
        return depositService.getAllCustomersOfDeposit(depositNumber);
    }

    @PostMapping(path = "/change-status")
    public String changeStatus(@RequestParam(value = "depositNumber") Long depositNumber,
                               @RequestParam(value = "status") int status){

        return depositService.changeStatus(depositNumber, status);
    }

    @PostMapping(path = "/defray")
    public String defray(@RequestParam("depositNumber") Long depositNumber,
                         @RequestParam("balance") Long balance){
        return depositService.defray(depositNumber, balance);
    }

    @PostMapping(path = "/withdrawal")
    public String withdrawal(@RequestParam("depositNumber") Long depositNumber,
                             @RequestParam("balance") Long balance){
        return depositService.withdrawal(depositNumber, balance);
    }

    @PostMapping(path = "/transfer")
    public String transfer(@RequestParam("sourceDepositNumber") Long sourceDepositNumber,
                           @RequestParam("destinationDepositNumber") Long destinationDepositNumber,
                           @RequestParam("balance") Long balance){
        return depositService.transfer(sourceDepositNumber, destinationDepositNumber, balance);
    }

    @PostMapping(path = "/get-balance")
    public String transfer(@RequestParam("depositNumber") Long depositNumber){
        return depositService.getBalance(depositNumber);
    }

    @GetMapping(path = "/get-deposit-by-customer-id")
    public List<Deposit> getDepositsByCustomerId(@RequestParam("customerId") Long customerId){
        return depositService.getDepositsByCustomerId(customerId);
    }

}
