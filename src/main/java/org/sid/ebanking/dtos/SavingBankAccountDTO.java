package org.sid.ebanking.dtos;

import lombok.Data;
import org.sid.ebanking.enums.AccountStatus;
import java.util.Date;



@Data

public class SavingBankAccountDTO extends BankAccountDTO  {
    private String id;
    private double solde;
    private Date createdAt;
    private AccountStatus status;
    private ClientDTO clientDTO;
    private double interestRate;

}
