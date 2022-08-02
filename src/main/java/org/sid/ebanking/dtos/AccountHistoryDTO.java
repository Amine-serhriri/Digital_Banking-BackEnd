package org.sid.ebanking.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {


    private String account_id;
    private double solde;
    private String type;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO>accountOperationDTOS;
}
