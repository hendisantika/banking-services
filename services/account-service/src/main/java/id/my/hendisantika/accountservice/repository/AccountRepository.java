package id.my.hendisantika.accountservice.repository;

import id.my.hendisantika.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCustomer_Id(Long customerId);

    @Transactional
    @Modifying
    void deleteByCustomer_Id(Long customerId);
}
