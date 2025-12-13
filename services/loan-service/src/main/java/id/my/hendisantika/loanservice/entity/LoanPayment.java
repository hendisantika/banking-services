package id.my.hendisantika.loanservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "loan_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Double amount;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
