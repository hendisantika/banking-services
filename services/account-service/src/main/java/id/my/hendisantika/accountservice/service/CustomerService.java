package id.my.hendisantika.accountservice.service;

import id.my.hendisantika.accountservice.dto.CardDto;
import id.my.hendisantika.accountservice.dto.CustomerDetailsDto;
import id.my.hendisantika.accountservice.dto.LoanDto;
import id.my.hendisantika.accountservice.entity.Account;
import id.my.hendisantika.accountservice.entity.Customer;
import id.my.hendisantika.accountservice.exception.ResourceNotFoundException;
import id.my.hendisantika.accountservice.mapper.AccountMapper;
import id.my.hendisantika.accountservice.mapper.CustomerMapper;
import id.my.hendisantika.accountservice.repository.AccountRepository;
import id.my.hendisantika.accountservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.43
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private final CardsClient cardsClient;
    private final LoansClient loansClient;

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByPhone(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomer_Id(customer.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "customerId", customer.getId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.toCustomerDetailsDto(customer);
        customerDetailsDto.setAccount(AccountMapper.toDto(account));

        ResponseEntity<LoanDto> loanResponse = loansClient.fetchLoan(mobileNumber);

        if (loanResponse != null)
            customerDetailsDto.setLoan(loanResponse.getBody());

        ResponseEntity<CardDto> cardResponse = cardsClient.fetchCard(mobileNumber);

        if (cardResponse != null)
            customerDetailsDto.setCard(cardResponse.getBody());

        return customerDetailsDto;

    }
}
