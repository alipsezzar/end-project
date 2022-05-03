package com.tosan.deposit.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table
public class Deposit {

    @Id
    @SequenceGenerator(
            name="deposit_sequence",
            sequenceName = "deposit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_sequence"
    )
    private Long id;
    private DepositType depositType;
    private String title;
    private DepositStatus status;
    @Column(unique = true)
    private Long depositNumber;
    private AccountCurrency currency;
    private BigDecimal balance;
    private LocalDate startDate;
    private Date expireDate;
    private Long customerId;

    public Deposit(DepositType depositType, String title, DepositStatus status, Long depositNumber, AccountCurrency currency, BigDecimal balance, LocalDate startDate, Date expireDate, Long customerId) {
        this.depositType = depositType;
        this.title = title;
        this.status = status;
        this.depositNumber = depositNumber;
        this.currency = currency;
        this.balance = balance;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.customerId = customerId;
    }

    // testing constructor
    public Deposit(DepositStatus status){
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public String getTitle() {
        return title;
    }

    public DepositStatus getStatus() {
        return status;
    }

    public Long getDepositNumber() {
        return depositNumber;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public static Long getDepoNumber() {
        return depoNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(DepositStatus status) {
        this.status = status;
    }

    public void setDepositNumber(Long depositNumber) {
        this.depositNumber = depositNumber;
    }

    public void setCurrency(AccountCurrency currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) > 0){
            this.balance = balance;
        }
        else{
            throw new IllegalStateException("Negative balance");
        }
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public static void setDepoNumber(Long depoNumber) {
        Deposit.depoNumber = depoNumber;
    }

    public static Date nextYearDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    private static Long depoNumber = 12345678L;
    public static Long generateDepositNumber(){
        return depoNumber += 1;
    }
}
