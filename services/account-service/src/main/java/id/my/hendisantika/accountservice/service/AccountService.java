package id.my.hendisantika.accountservice.service;

import id.my.hendisantika.accountservice.common.AccountsConstants;
import id.my.hendisantika.accountservice.dto.AccountDto;
import id.my.hendisantika.accountservice.dto.AccountMsgDto;
import id.my.hendisantika.accountservice.dto.CustomerDto;
import id.my.hendisantika.accountservice.entity.Account;
import id.my.hendisantika.accountservice.entity.Customer;
import id.my.hendisantika.accountservice.exception.CustomerAlreadyExistsException;
import id.my.hendisantika.accountservice.exception.ResourceNotFoundException;
import id.my.hendisantika.accountservice.mapper.AccountMapper;
import id.my.hendisantika.accountservice.mapper.CustomerMapper;
import id.my.hendisantika.accountservice.repository.AccountRepository;
import id.my.hendisantika.accountservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.41
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    @Transactional
    public void createAccount(CustomerDto customerDto) {

        Optional<Customer> optionalCustomer = customerRepository.findByPhone(customerDto.getPhone());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getPhone());
        }

        Customer customer = CustomerMapper.toEntity(customerDto);

        Account acc = createNewAccount();
        acc.setCustomer(customer);
        customer.setAccount(acc); // ---> اوبشنال عشان الميموري كونسيستينسيي

        accountsRepository.save(acc);

        //Customer savedCustomer = customerRepository.save(customer);
        // NOTICE *** -> اللي عنده ال كاسكيد هو اللي هيسيف .. الاونر هو اللي هي سيت العلاقه

        this.sendCommunication(acc, customer);
    }


    private Account createNewAccount() {
        Account newAccount = new Account();
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    private void sendCommunication(Account account, Customer customer) {

        var accountsMsgDto = new AccountMsgDto(account.getAccountNumber(),
                account.getAccountType(),
                customer.getEmail(),
                customer.getPhone()
        );

        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendEmail-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByPhone(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountsRepository.findByCustomer_Id(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getId().toString())
        );
        CustomerDto customerDto = CustomerMapper.toDto(customer);
        customerDto.setAccountDto(AccountMapper.toDto(account));
        return customerDto;
    }


    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountsDto = customerDto.getAccountDto();
        if (accountsDto != null) {
            Account account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            Account updatedAccount = AccountMapper.toEntity(accountsDto);
            updatedAccount.setCustomer(account.getCustomer());
            accountsRepository.save(updatedAccount);

            Customer customer = account.getCustomer();

            Customer updatedCustomer = CustomerMapper.toEntity(customerDto);
            updatedCustomer.setId(customer.getId());
            customerRepository.save(customer);

            isUpdated = true;
        }
        return isUpdated;
    }


    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByPhone(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomer_Id(customer.getId());
        customerRepository.deleteById(customer.getId());
        return true;
    }
}
