package id.my.hendisantika.accountservice.mapper;

import id.my.hendisantika.accountservice.dto.TransactionDto;
import id.my.hendisantika.accountservice.entity.Account;
import id.my.hendisantika.accountservice.entity.Transaction;
import id.my.hendisantika.accountservice.entity.TransactionType;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.39
 * To change this template use File | Settings | File Templates.
 */
public class TransactionMapper {
    public static Transaction toEntity(TransactionDto dto, Account toAcc, Account fromAcc) {
        Transaction transaction = new Transaction();

        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());
        transaction.setType(TransactionType.valueOf(dto.getType().toUpperCase()));

        if (transaction.getType() == TransactionType.DEPOSIT)
            transaction.setToAccount(toAcc);
        else if (transaction.getType() == TransactionType.WITHDRAWAL)
            transaction.setFromAccount(fromAcc);
        else {
            transaction.setToAccount(toAcc);
            transaction.setFromAccount(fromAcc);
        }

        return transaction;
    }

    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setType(transaction.getType().toString());

        if (transaction.getType() == TransactionType.DEPOSIT)
            transactionDto.setToAccountId(transactionDto.getToAccountId());
        else if (transaction.getType() == TransactionType.WITHDRAWAL)
            transactionDto.setFromAccountId(transactionDto.getFromAccountId());
        else {
            transactionDto.setToAccountId(transactionDto.getToAccountId());
            transactionDto.setFromAccountId(transactionDto.getFromAccountId());
        }

        return transactionDto;
    }
}
