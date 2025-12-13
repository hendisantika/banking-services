package id.my.hendisantika.accountservice.service.client;

import id.my.hendisantika.accountservice.dto.CardDto;
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
 * Time: 09.45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CardsClientFallBack implements CardsClient {
    @Override
    public ResponseEntity<CardDto> fetchCard(String mobileNumber) {
        return null;
    }
}
