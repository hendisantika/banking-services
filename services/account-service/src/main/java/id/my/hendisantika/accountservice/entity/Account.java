package id.my.hendisantika.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 09.32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long accountNumber;

    @Column(name = "account_type")
    @NotBlank
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> inwardTransactions;

    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> outwardTransactions;
}
