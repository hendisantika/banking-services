package id.my.hendisantika.accountservice.controller;

import id.my.hendisantika.accountservice.dto.CustomerDetailsDto;
import id.my.hendisantika.accountservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.49
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam String mobile) {
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobile);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
