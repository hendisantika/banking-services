package id.my.hendisantika.accountservice.service.client;

import id.my.hendisantika.accountservice.dto.CardDto;
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
 * Time: 09.45
 * To change this template use File | Settings | File Templates.
 */
@FeignClient(
        value = "cards",
        url = "${microservices.cards.url}",
        fallback = CardsClientFallBack.class
)
public interface CardsClient {
    @GetMapping("/api/cards/{mobileNumber}")
    ResponseEntity<CardDto> fetchCard(@PathVariable String mobileNumber);
}
