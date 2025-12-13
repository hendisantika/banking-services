package id.my.hendisantika.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ApiGatewayApplication {
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2, 2);
    }

    @Bean
    public KeyResolver keyResolver() {
        return exchange -> Mono.just(exchange
                .getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress()
        );
    }

    static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
