package com.tosan.transaction.controllers;

import com.tosan.transaction.data.Transaction;
import com.tosan.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/register")
    public String registerTransaction(@RequestParam("transactionType") Long transactionType,
                                      @RequestParam("balance") Long balance,
                                      @RequestParam("status") Long transactionStatus,
                                      @RequestParam("sDepositNumber") Long sDepositNumber,
                                      @RequestParam("dDepositNumber") Long dDepositNumber){
        return transactionService.registerTransaction(transactionType,
                balance, transactionStatus, sDepositNumber, dDepositNumber);
    }

    @PostMapping("/get-all")
    public List<Transaction> getAllTransactions(@RequestParam("depositNumber") Long depositNumber){
        return transactionService.getAllTransactions(depositNumber);
    }

    @PostMapping("/get-by-tracking-number")
    public Transaction getByTrackingNumber(@RequestParam("trackingNumber") Long trackingNumber){
        return transactionService.getByTrackingNumber(trackingNumber);
    }
}
