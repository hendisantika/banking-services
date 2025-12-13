package id.my.hendisantika.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.31
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransactionDto {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String type;

    private Long toAccountId;

    private Long fromAccountId;

    private LocalDateTime date = LocalDateTime.now();
}
