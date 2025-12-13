package id.my.hendisantika.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.26
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    @NotBlank
    private Long accountNumber;

    @NotBlank
    private String accountType;

    @NotBlank
    private String branchAddress;
}
