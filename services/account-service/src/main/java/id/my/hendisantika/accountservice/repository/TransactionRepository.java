package id.my.hendisantika.accountservice.repository;

import id.my.hendisantika.accountservice.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.40
 * To change this template use File | Settings | File Templates.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.fromAccount.accountNumber = :accountId
            OR t.toAccount.accountNumber = :accountId
            """)
    List<Transaction> findByAccountId(@Param("accountId") Long accountId);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.toAccount.accountNumber = :accountId
            """)
    List<Transaction> findInwardTransactions(@Param("accountId") Long accountId);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.fromAccount.accountNumber = :accountId
            """)
    List<Transaction> findOutwardTransactions(@Param("accountId") Long accountId);

    List<Transaction> findAll(Specification<Transaction> spec);
}
