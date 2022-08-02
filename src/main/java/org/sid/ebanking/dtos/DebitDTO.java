package org.sid.ebanking.dtos;

import lombok.Data;

@Data
public class DebitDTO {

    private String account_id;
    private double amount;
    private String description;
}
