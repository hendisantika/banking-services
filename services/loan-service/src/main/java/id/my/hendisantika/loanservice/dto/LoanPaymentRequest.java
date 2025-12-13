package id.my.hendisantika.loanservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.46
 * To change this template use File | Settings | File Templates.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanPaymentRequest {

    @Positive
    @NotNull
    private Double amount;

    @NotBlank
    private String mobile;

    private LocalDateTime timestamp;

    @NotNull
    private Long loanId;
}
