package org.sid.ebanking.service;

import org.sid.ebanking.Exception.BankAccountNotFoundException;
import org.sid.ebanking.Exception.ClientNotFoundException;
import org.sid.ebanking.Exception.soldeNotSufficentException;
import org.sid.ebanking.dtos.*;
import org.sid.ebanking.entities.BankAccount;
import org.sid.ebanking.entities.Client;
import org.sid.ebanking.entities.CurrentAccount;
import org.sid.ebanking.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

    public ClientDTO saveClient(ClientDTO clientDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialSolde, double overdraft, Long clientId) throws ClientNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialSolde, double interestRate, Long clientId) throws ClientNotFoundException;
    List<ClientDTO>CLIENT_LIST();
    BankAccountDTO consulter(String AccountId) throws BankAccountNotFoundException;
    void debit(String accountId,double montant,String description) throws BankAccountNotFoundException, soldeNotSufficentException;
    void credit(String accountId,double montant,String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource,String accountIdDestination,double montant) throws BankAccountNotFoundException, soldeNotSufficentException;

    List<BankAccountDTO> bankAccount_LIST();

    ClientDTO getClient(Long client_id) throws ClientNotFoundException;

    ClientDTO updateClient(ClientDTO clientDTO);

    void  deleteclient(Long id);

    List<AccountOperationDTO>accountHistorique(String account_id);


    public  AccountHistoryDTO getAccountHistorique(String account_id, int page, int size) throws BankAccountNotFoundException;


    List<ClientDTO> searchClient(String keyword);
}
