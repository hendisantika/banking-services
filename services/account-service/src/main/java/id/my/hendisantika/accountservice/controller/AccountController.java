package id.my.hendisantika.accountservice.controller;

import id.my.hendisantika.accountservice.dto.CustomerDto;
import id.my.hendisantika.accountservice.service.AccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.48
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/testCircuitBreaker")
    public ResponseEntity<?> testCircuitBreaker() {
        throw new RuntimeException("testFaultTolerance");
    }

    @GetMapping("/testRetry")
    public ResponseEntity<?> testRetry() {
        log.debug("I should be shown 3 times..\uD83D\uDC85");
        throw new RuntimeException("testRetry");
    }

    @GetMapping("/testTimeout")
    public ResponseEntity<?> testTimeout() {

        log.debug("put breakpoint here");
        return ResponseEntity.ok("I WILL BE SHOWN IF YOU REMOVE BREAKPOINT ONLY");
    }

    @RateLimiter(name = "accRL", fallbackMethod = "testRLFallback")
    @GetMapping("/testRateLimiter")
    public ResponseEntity<?> testRL() {
        log.debug("I have tokens ..");
        return ResponseEntity.ok("I have tokens ..");
    }

    public ResponseEntity<?> testRLFallback(Throwable t) {
        log.debug("Tokens Gone away :(");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }


    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Account created successfully for customer with mobile: " + customerDto.getPhone());
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> fetchAccount(@PathVariable String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping
    public ResponseEntity<String> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean updated = accountService.updateAccount(customerDto);
        if (updated) {
            return ResponseEntity.ok("Account updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No account details provided to update.");
        }
    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String mobileNumber) {
        boolean deleted = accountService.deleteAccount(mobileNumber);
        if (deleted) {
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete account.");
        }
    }
}
