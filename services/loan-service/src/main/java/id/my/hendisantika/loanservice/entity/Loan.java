package id.my.hendisantika.loanservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 14/12/25
 * Time: 06.47
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private Double totalLoan;

    private Double amountPaid;

    private Double outstandingAmount;
}
