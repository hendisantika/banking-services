package id.my.hendisantika.loanservice.mapper;

import id.my.hendisantika.loanservice.dto.LoanPaymentRequest;
import id.my.hendisantika.loanservice.dto.LoanPaymentResponse;
import id.my.hendisantika.loanservice.entity.Loan;
import id.my.hendisantika.loanservice.entity.LoanPayment;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.51
 * To change this template use File | Settings | File Templates.
 */
public class PaymentMapper {
    public static LoanPaymentResponse toLoanPaymentResponse(LoanPayment payment, Double amount) {
        return LoanPaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .timestamp(payment.getTimestamp())
                .LoanId(payment.getLoan().getLoanId())
                .currentAmount(amount)
                .build();
    }

    public static LoanPayment toEntity(LoanPaymentRequest request, Loan loan) {
        return LoanPayment.builder()
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .loan(loan)
                .build();
    }
}
