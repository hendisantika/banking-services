package id.my.hendisantika.notificationservice;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 07.00
 * To change this template use File | Settings | File Templates.
 */
public record AccountMsgDto(
        Long accountNumber,
        String accountType,
        String email,
        String phone
) {
}
