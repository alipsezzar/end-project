package com.tosan.deposit.services;


import com.tosan.deposit.data.AccountCurrency;
import com.tosan.deposit.data.Deposit;
import com.tosan.deposit.data.DepositStatus;
import com.tosan.deposit.data.DepositType;
import com.tosan.deposit.repositories.DepositRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(DepositService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository){
        this.depositRepository = depositRepository;
    }

    public void openDeposit(Long customerId, int depositType, String fName, String lName, Long initBalance){
        String url = String.format("http://localhost:8080/customer/customer-exists?customerId=%d", customerId);
        Boolean customerExists = restTemplate.getForObject(url, Boolean.class);
        if (Boolean.TRUE.equals(customerExists)){
            Deposit deposit = new Deposit(
                    DepositType.values()[depositType],
                    fName + lName + DepositType.values()[depositType].name(),
                    DepositStatus.OPEN,
                    Deposit.generateDepositNumber(),
                    AccountCurrency.RIAL,
                    new BigDecimal(initBalance),
                    LocalDate.now(),
                    Deposit.nextYearDate(),
                    customerId
            );

            depositRepository.save(deposit);
            LOGGER.info("new deposit added");
        }
        else{
            LOGGER.error("No customer with this id");
            throw new IllegalStateException("No customer with this id");
        }
    }

    public List<Deposit> getAllDeposits(){
        return depositRepository.findAll();
    }

    public Object getAllCustomersOfDeposit(Long depositNumber){
        Deposit deposit = depositRepository.findByDepositNumber(depositNumber);
        LOGGER.info("Calling get customer API");
        String url = String.format("http://localhost:8080/customer/get-customer?customerId=%d", deposit.getCustomerId());
        return restTemplate.getForObject(url, Object.class);
    }

    public String changeStatus(Long depositNumber, int status){
        Deposit deposit = depositRepository.findByDepositNumber(depositNumber);
        if (deposit != null) {
            if (deposit.getStatus() != DepositStatus.CLOSE){
                deposit.setStatus(DepositStatus.values()[status]);
                LOGGER.info("status of deposit with this deposit number: " + depositNumber + "changed");
                depositRepository.save(deposit);
                LOGGER.info("status change saved!");
                return "successful";
            }
        }
        LOGGER.error("Wrong deposit number");
        throw new IllegalStateException();
    }

    public String defray(Long depositNumber, Long balance){
        Deposit deposit = depositRepository.findByDepositNumber(depositNumber);
        if (deposit != null){
            if ((deposit.getStatus() == DepositStatus.OPEN) || (deposit.getStatus() == DepositStatus.WITHDRAWALBLOCK)){
                if (balance > 0){
                    deposit.setBalance(deposit.getBalance().add(new BigDecimal(balance)));
                    LOGGER.info("Balance of deposit with this number: " + depositNumber + "increased");
                    depositRepository.save(deposit);
                    LOGGER.info("Saved deposit");

                    LOGGER.info("Calling transaction registration API...");
                    String url = String.format("http://localhost:8082/transaction/register?transactionType=0&balance=%d&status=0&sDepositNumber=%d&dDepositNumber=0",
                            balance, depositNumber);
                    restTemplate.getForObject(url,
                            String.class);
                    LOGGER.info("Registered Transaction");
                    return "Successful now balance is " + deposit.getBalance().toString();
                }
                else {
                    LOGGER.error("Negative amount for balance");
                    throw new IllegalStateException("Negative balance");
                }
            }
            LOGGER.info("Calling transaction registration API...");
            String url = String.format("http://localhost:8082/transaction/register?transactionType=1&balance=%d&status=1&sDepositNumber=%d&dDepositNumber=0",
                    balance, depositNumber);
            restTemplate.getForObject(url,
                    String.class);
            LOGGER.info("Registered Transaction");
            LOGGER.error("Transaction was unsuccessful. Deposit status problem");
            throw new IllegalStateException();
        }
        LOGGER.error("Wrong deposit number");
        throw new IllegalStateException();
    }

    public String withdrawal(Long depositNumber, Long balance){
        Deposit deposit = depositRepository.findByDepositNumber(depositNumber);
        if (deposit != null){
            if ((deposit.getStatus() == DepositStatus.OPEN) || (deposit.getStatus() == DepositStatus.DEFRAYBLOCK)){
                if (balance > 0){
                    deposit.setBalance(deposit.getBalance().subtract(new BigDecimal(balance)));
                    LOGGER.info("Balance of deposit with this number: " + depositNumber + "decreased");
                    depositRepository.save(deposit);
                    LOGGER.info("Deposit saved");
                    LOGGER.info("Calling Transaction API...");
                    String url = String.format("http://localhost:8082/transaction/register?transactionType=0&balance=%d&status=0&sDepositNumber=0&dDepositNumber=%d",balance, depositNumber);
                    restTemplate.getForObject(url,
                            String.class);
                    LOGGER.info("Transaction registration done");
                    return "Successful now balance is " + deposit.getBalance().toString();
                }
                LOGGER.error("negative balance");
                throw new IllegalStateException();
            }
            String url = String.format("http://localhost:8082/transaction/register?transactionType=0&balance=%d&status=1&sDepositNumber=0&dDepositNumber=%d",balance, depositNumber);
            restTemplate.getForObject(url,
                    String.class);
            LOGGER.error("Deposit status problem");
            throw new IllegalStateException();
        }
        LOGGER.error("Wrong deposit number");
        throw new IllegalStateException();
    }

    @Transactional
    public String transfer(Long sourceDepositNumber, Long destinationDepositNumber, Long balance){
        if (balance > 0){
            Deposit sourceDeposit = depositRepository.findByDepositNumber(sourceDepositNumber);
            Deposit destinationDeposit = depositRepository.findByDepositNumber(destinationDepositNumber);
            if (sourceDeposit != null && destinationDeposit != null){
                if ((sourceDeposit.getStatus() == DepositStatus.OPEN) || (sourceDeposit.getStatus() == DepositStatus.DEFRAYBLOCK)
                        && (destinationDeposit.getStatus() == DepositStatus.OPEN) || (destinationDeposit.getStatus() == DepositStatus.WITHDRAWALBLOCK)){
                    try {
                        sourceDeposit.setBalance(sourceDeposit.getBalance().subtract(new BigDecimal(balance)));
                        destinationDeposit.setBalance(destinationDeposit.getBalance().add(new BigDecimal(balance)));
                    } catch (Exception e){
                        LOGGER.error("Negative Number");
                        throw new IllegalStateException();
                    }
                    depositRepository.save(sourceDeposit);
                    depositRepository.save(destinationDeposit);
                    String url = String.format("http://localhost:8082/transaction/register?transactionType=2&balance=%d&status=0&sDepositNumber=%d&dDepositNumber=%d",balance,sourceDepositNumber, destinationDepositNumber);
                    LOGGER.info("Calling transaction registration API...");
                    restTemplate.getForObject(url,
                            String.class);
                    LOGGER.info("Successful transaction registered");
                    return "Successful";
                }
                String url = String.format("http://localhost:8082/transaction/register?transactionType=2&balance=%d&status=1&sDepositNumber=%d&dDepositNumber=%d",balance,sourceDepositNumber, destinationDepositNumber);
                LOGGER.info("Calling transaction registration API...");
                restTemplate.getForObject(url,
                        String.class);
                LOGGER.info("Unsuccessful transaction registered");
                LOGGER.error("Deposit status problem");
                throw new IllegalStateException();
            }
            LOGGER.error("Wrong Deposit Number");
            throw new IllegalStateException();
        }
        LOGGER.error("negative balance");
        throw new IllegalStateException();
    }

    public String getBalance(Long depositNumber){
        Deposit deposit = depositRepository.findByDepositNumber(depositNumber);
        if (deposit != null){
            return "deposit balance is: " + deposit.getBalance().toString();
        }
        LOGGER.error("Wrong deposit Number");
        throw new IllegalStateException();
    }

    public List<Deposit> getDepositsByCustomerId(Long customerId){
        return depositRepository.findAllByCustomerId(customerId);
    }
}
