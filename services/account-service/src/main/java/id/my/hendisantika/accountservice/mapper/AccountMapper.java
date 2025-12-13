package id.my.hendisantika.accountservice.mapper;

import id.my.hendisantika.accountservice.dto.AccountDto;
import id.my.hendisantika.accountservice.entity.Account;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.38
 * To change this template use File | Settings | File Templates.
 */
public class AccountMapper {

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();

        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBranchAddress(dto.getBranchAddress());

        return account;
    }

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }
}
