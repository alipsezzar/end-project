package com.tosan.customer.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private Long nationalCode;
    private LocalDate bod;
    private Long customerCode;
    private Status status;
    private CustomerType customerType;
    private String mobileNumber;
    private LocalDate customerDefinitionDate;

    public Customer(String name, Long nationalCode, LocalDate bod, Long customerCode, Status status, CustomerType customerType, String mobileNumber, LocalDate customerDefinitionDate) {
        this.name = name;
        this.nationalCode = nationalCode;
        this.bod = bod;
        this.customerCode = customerCode;
        this.status = status;
        this.customerType = customerType;
        this.mobileNumber = mobileNumber;
        this.customerDefinitionDate = customerDefinitionDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNationalCode() {
        return nationalCode;
    }

    public LocalDate getBod() {
        return bod;
    }

    public Long getCustomerCode() {
        return customerCode;
    }

    public Status getStatus() {
        return status;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public LocalDate getCustomerDefinitionDate() {
        return customerDefinitionDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationalCode(Long nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setBod(LocalDate bod) {
        this.bod = bod;
    }

    public void setCustomerCode(Long customerCode) {
        this.customerCode = customerCode;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setCustomerDefinitionDate(LocalDate customerDefinitionDate) {
        this.customerDefinitionDate = customerDefinitionDate;
    }

    private static Long customerSeed = 11111111L;
    public static Long generateCustomerCode(){
        return customerSeed++;
    }
}
