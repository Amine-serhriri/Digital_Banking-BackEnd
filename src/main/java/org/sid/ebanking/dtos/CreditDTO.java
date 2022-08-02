package org.sid.ebanking.dtos;

import lombok.Data;

@Data
public class CreditDTO {

    private String account_id;
    private double amount;
    private String description;
}
