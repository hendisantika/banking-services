package id.my.hendisantika.loanservice.service;

import id.my.hendisantika.loanservice.dto.LoanPaymentRequest;
import id.my.hendisantika.loanservice.dto.LoanPaymentResponse;
import id.my.hendisantika.loanservice.entity.Loan;
import id.my.hendisantika.loanservice.entity.LoanPayment;
import id.my.hendisantika.loanservice.exception.ResourceNotFoundException;
import id.my.hendisantika.loanservice.mapper.PaymentMapper;
import id.my.hendisantika.loanservice.repository.LoansRepository;
import id.my.hendisantika.loanservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.54
 * To change this template use File | Settings | File Templates.
 */
@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final LoansRepository loansRepository;

    public LoanPaymentResponse pay(LoanPaymentRequest request) {
        Loan loan = loansRepository.findById(request.getLoanId()).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "Id", request.getLoanId().toString()));

        loan.setAmountPaid(loan.getAmountPaid() + request.getAmount());
        Double restAmount = loan.getAmountPaid() - request.getAmount();

        LoanPayment payment = PaymentMapper.toEntity(request, loan);
        LoanPaymentResponse response = PaymentMapper.toLoanPaymentResponse(payment, restAmount);

        return response;
    }

    public List<LoanPaymentResponse> findAllByLoanId(Long loanId) {

        List<LoanPayment> payments = paymentRepository.findAllByLoan_LoanId(loanId);
        return payments.stream().map(p -> {
            Double rest = p.getLoan().getTotalLoan() - p.getLoan().getAmountPaid();
            return PaymentMapper.toLoanPaymentResponse(p, rest);
        }).toList();
    }
}
