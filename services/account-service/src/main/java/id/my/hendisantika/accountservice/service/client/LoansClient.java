package id.my.hendisantika.accountservice.service.client;

import id.my.hendisantika.accountservice.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.46
 * To change this template use File | Settings | File Templates.
 */
@FeignClient(
        value = "loans",
        url = "${microservices.loans.url}",
        fallback = LoansClientFallBack.class
)
public interface LoansClient {

    @GetMapping("/api/loans/{mobileNumber}")
    ResponseEntity<LoanDto> fetchLoan(@PathVariable String mobileNumber);
}
