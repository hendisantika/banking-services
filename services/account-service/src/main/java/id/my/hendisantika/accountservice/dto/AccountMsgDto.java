package id.my.hendisantika.accountservice.dto;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.27
 * To change this template use File | Settings | File Templates.
 */
public record AccountMsgDto(
        Long accountNumber,
        String accountType,
        String email,
        String phone
) {
}