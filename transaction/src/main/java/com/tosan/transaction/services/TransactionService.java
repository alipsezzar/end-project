package com.tosan.transaction.services;

import com.tosan.transaction.repositories.TransactionRepository;
import com.tosan.transaction.data.Transaction;
import com.tosan.transaction.data.TransactionStatus;
import com.tosan.transaction.data.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public String registerTransaction(Long transactionType, Long balance, Long transactionStatus,
                                      Long sDepositNumber, Long dDepositNumber){
        Transaction transaction = new Transaction(
                LocalDate.now(),
                new BigDecimal(balance),
                TransactionStatus.values()[transactionStatus.intValue()],
                TransactionType.values()[transactionType.intValue()],
                sDepositNumber,
                dDepositNumber,
                Transaction.generateTrackingNumber(),
                "No description"
        );

        transactionRepository.save(transaction);
        LOGGER.info("new transaction added");
        return "Transaction added successfully";
    }

    public List<Transaction> getAllTransactions(Long depositNumber){
        List<Transaction> transactions = transactionRepository.findAllByDepositNumber(depositNumber);
        if (transactions.isEmpty()){
            LOGGER.error("Wrong deposit number");
            throw new IllegalStateException();
        }
        return transactions;
    }

    public Transaction getByTrackingNumber(Long trackingNumber){
        Transaction transaction = transactionRepository.findByTrackingNumber(trackingNumber);
        if (transaction != null){
            return transaction;
        }
        LOGGER.error("Wrong tracking number");
        throw new IllegalStateException();
    }
}
