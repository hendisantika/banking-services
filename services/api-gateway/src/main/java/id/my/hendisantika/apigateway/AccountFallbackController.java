package id.my.hendisantika.apigateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.37
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/fallback")
public class AccountFallbackController {

    @RequestMapping("/contactSupport")
    public Mono<ResponseEntity<String>> contactSupport() {
        return Mono.just(
                ResponseEntity
                        .status(200)
                        .body("Fallback response: service temporarily unavailable")
        );
    }
}
