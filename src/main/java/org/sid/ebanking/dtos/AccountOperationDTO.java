package org.sid.ebanking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking.entities.BankAccount;
import org.sid.ebanking.enums.OperationType;

import javax.persistence.*;
import java.util.Date;


@Data

public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount ;
    private OperationType type;
    private String description;
}
