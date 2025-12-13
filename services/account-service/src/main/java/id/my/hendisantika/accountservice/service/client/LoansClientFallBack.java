package id.my.hendisantika.accountservice.service.client;

import id.my.hendisantika.accountservice.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
@Component
public class LoansClientFallBack implements LoansClient {
    @Override
    public ResponseEntity<LoanDto> fetchLoan(String mobileNumber) {
        return null;
    }
}
