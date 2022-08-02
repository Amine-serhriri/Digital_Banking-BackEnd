package org.sid.ebanking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4, discriminatorType =DiscriminatorType.STRING)
@Data
@AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id
    private String id;
    private double solde;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy ="bankAccount" ,fetch = FetchType.LAZY)
    private List<AccountOperation>accountOperations;
}
