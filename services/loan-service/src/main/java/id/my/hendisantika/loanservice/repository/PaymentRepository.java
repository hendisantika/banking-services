package id.my.hendisantika.loanservice.repository;

import id.my.hendisantika.loanservice.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.52
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentRepository extends JpaRepository<LoanPayment, Long> {
    List<LoanPayment> findAllByLoan_LoanId(Long loanId);
}
