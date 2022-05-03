package com.tosan.transaction.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private BigDecimal balance;
    private TransactionStatus status;
    private TransactionType type;
    private Long sourceDepositNumber;
    private Long destinationDepositNumber;
    private Long trackingNumber;
    private String description;
    private static Long trackingNumberSeed = 123456789L;

    public Transaction(LocalDate date, BigDecimal balance, TransactionStatus status, TransactionType type,
                       Long sourceDepositNumber, Long destinationDepositNumber, Long trackingNumber, String description){
        this.date = date;
        this.balance = balance;
        this.status = status;
        this.type = type;
        this.sourceDepositNumber = sourceDepositNumber;
        this.destinationDepositNumber = destinationDepositNumber;
        this.trackingNumber = trackingNumber;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public TransactionType getType() {
        return type;
    }

    public Long getSourceDepositNumber() {
        return sourceDepositNumber;
    }

    public Long getDestinationDepositNumber() {
        return destinationDepositNumber;
    }

    public Long getTrackingNumber() {
        return trackingNumber;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setSourceDepositNumber(Long sourceDepositNumber) {
        this.sourceDepositNumber = sourceDepositNumber;
    }

    public void setDestinationDepositNumber(Long destinationDepositNumber) {
        this.destinationDepositNumber = destinationDepositNumber;
    }

    public static Long generateTrackingNumber() {
        trackingNumberSeed += 1;
        return trackingNumberSeed;
    }
}
