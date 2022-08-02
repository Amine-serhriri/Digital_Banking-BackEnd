package org.sid.ebanking.web;

import lombok.AllArgsConstructor;
import org.sid.ebanking.Exception.BankAccountNotFoundException;
import org.sid.ebanking.Exception.soldeNotSufficentException;
import org.sid.ebanking.dtos.*;
import org.sid.ebanking.entities.BankAccount;
import org.sid.ebanking.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{account_id}")
    public BankAccountDTO consulter(@PathVariable String account_id) throws BankAccountNotFoundException {
        return bankAccountService.consulter(account_id);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO>listAccounts(){
        return bankAccountService.bankAccount_LIST();
    }
    @GetMapping("accounts/{account_id}/operation")
    public List<AccountOperationDTO> getHistorique(@PathVariable String account_id){
        return bankAccountService.accountHistorique(account_id);

    }
    @GetMapping("accounts/{account_id}/pageOperations")
    public AccountHistoryDTO getAccountHistorique(@PathVariable String account_id,
                                                        @RequestParam(name = "page",defaultValue = "0") int page,
                                                        @RequestParam(name = "size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistorique(account_id,page,size);

    }
    @PostMapping ("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, soldeNotSufficentException {
        bankAccountService.debit(debitDTO.getAccount_id(),debitDTO.getAmount(),debitDTO.getDescription());
        System.out.println(debitDTO);
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        bankAccountService.credit(creditDTO.getAccount_id(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfert(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, soldeNotSufficentException {
        bankAccountService.transfert(transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }



}
