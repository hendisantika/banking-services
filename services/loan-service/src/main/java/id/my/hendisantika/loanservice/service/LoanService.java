package id.my.hendisantika.loanservice.service;

import id.my.hendisantika.loanservice.common.LoansConstants;
import id.my.hendisantika.loanservice.dto.LoanDto;
import id.my.hendisantika.loanservice.entity.Loan;
import id.my.hendisantika.loanservice.exception.LoanAlreadyExistsException;
import id.my.hendisantika.loanservice.exception.ResourceNotFoundException;
import id.my.hendisantika.loanservice.mapper.LoanMapper;
import id.my.hendisantika.loanservice.repository.LoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.53
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoansRepository loansRepository;

    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }


    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0d);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }


    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoanMapper.toDto(loan);
    }


    public boolean updateLoan(LoanDto loansDto) {
        Loan loan = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.toEntity(loansDto);
        loansRepository.save(loan);
        return true;
    }


    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loan.getLoanId());
        return true;
    }
}
