package com.tosan.customer.services;


import com.tosan.customer.data.Customer;
import com.tosan.customer.data.CustomerType;
import com.tosan.customer.data.Status;
import com.tosan.customer.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public String defineCustomer(String name, Long nationalCode, String birthdate, String mobileNumber, int customerType){
        Long customerCode = Customer.generateCustomerCode();
        LocalDate bod = LocalDate.parse(birthdate);
        Customer customer = new Customer(name, nationalCode, bod, customerCode, Status.ACTIVE, CustomerType.values()[customerType], mobileNumber, LocalDate.now() );
        try {
            customerRepository.save(customer);
            LOGGER.info("New customer added");
            return "Successful";
        } catch (Exception e){
            return "Couldn't save customer" + e.getMessage();
        }

    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List getDepositsOfCustomer(Long nationalCode){
        Customer customer = customerRepository.findCustomerByNationalCode(nationalCode);
        if (customer != null){
            Long customerId = customer.getId();
            String url = String.format("http://localhost:8081/deposit/get-deposit-by-customer-id?customerId=%d", customerId);
            List deposits = new RestTemplate().getForObject(url, List.class);
            return deposits;
        }
        LOGGER.error("No customer with this national code: " + nationalCode);
        throw new IllegalStateException();
    }

    public void deleteCustomer(Long nationalCode) {
        Customer customer = customerRepository.findCustomerByNationalCode(nationalCode);
        if (customer != null) {
            String url = String.format("http://localhost:8081/deposit/get-deposit-by-customer-id?customerId=%d", customer.getId());
            List deposits = new RestTemplate().getForObject(url, List.class);
            assert deposits != null;
            if (deposits.isEmpty()){
                try {
                    customerRepository.delete(customer);
                    LOGGER.info("Customer with ID: " + customer.getId() + "deleted");
                } catch (Exception e){
                    LOGGER.error("Unable to delete customer");
                }
            }
            LOGGER.error("This customer still has deposits");
            throw new IllegalStateException();
        }
        LOGGER.error("Wrong national Code");
        throw new IllegalStateException();
    }

    public void changeCustomerStatus(Long nationalCode){
        Customer customer = customerRepository.findCustomerByNationalCode(nationalCode);
        if (customer != null){
            if (customer.getStatus() == Status.ACTIVE){
                customer.setStatus(Status.INACTIVE);
            }
            else{
                customer.setStatus(Status.ACTIVE);
            }
            try {
                customerRepository.save(customer);
            } catch (Exception e){
                LOGGER.error("Something Went wrong to save Customer in DB" + e.getMessage(), e);
            }

        }
        LOGGER.error("There's no customer with this national code: " + nationalCode);
        throw new IllegalStateException();
    }

    public Object getCustomer(Long customerId){
        Optional<Customer> customer = customerRepository.findById(customerId);
       if (customer.isPresent()){
           return customer.get();
        }
       LOGGER.error("No customer with this id");
       throw new IllegalStateException();
    }
}
