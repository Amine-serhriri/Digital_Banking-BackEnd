package org.sid.ebanking.service;

import org.sid.ebanking.Exception.BankAccountNotFoundException;
import org.sid.ebanking.Exception.ClientNotFoundException;
import org.sid.ebanking.Exception.soldeNotSufficentException;
import org.sid.ebanking.dtos.*;
import org.sid.ebanking.entities.*;
import org.sid.ebanking.enums.OperationType;
import org.sid.ebanking.mappers.BankAccountMapperImpl;
import org.sid.ebanking.repositories.AccountOperationRepository;
import org.sid.ebanking.repositories.BankAccountRepository;
import org.sid.ebanking.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImp implements BankAccountService{
    private ClientRepository clientRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
    Logger log = LoggerFactory.getLogger(this.getClass().getName());//ou bien on utilise l'annotation slf4j via lombok

    public BankAccountServiceImp(ClientRepository clientRepository,
                                 BankAccountRepository bankAccountRepository,
                                 AccountOperationRepository accountOperationRepository, BankAccountMapperImpl bankAccountMapper) {
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        log.info("saving new Account ");
        Client client=bankAccountMapper.fromClientDTO(clientDTO);
        Client client_Enregistre=clientRepository.save(client);
        return bankAccountMapper.fromClient(client_Enregistre);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialSolde, double overdraft, Long clientId) throws ClientNotFoundException {
        Client client=clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ClientNotFoundException("Client not found ");
        }
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setSolde(initialSolde);
        currentAccount.setClient(client);
        currentAccount.setOverDraft(overdraft);
        System.out.println( currentAccount.getOverDraft());
         CurrentAccount saveCurrentAccount=bankAccountRepository.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(saveCurrentAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialSolde, double interestRate, Long clientId) throws ClientNotFoundException {
        Client client=clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ClientNotFoundException("Client not found ");
        }
        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setSolde(initialSolde);
        savingAccount.setClient(client);
        savingAccount.setInterestRate(interestRate);
        SavingAccount saveSavingtAccount=bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(saveSavingtAccount);
    }


    @Override
    public List<ClientDTO> CLIENT_LIST() {
        List<Client>clientList = clientRepository.findAll();
        List<ClientDTO> clientDTO = clientList.stream()
                .map(client -> bankAccountMapper.fromClient(client))
                .collect(Collectors.toList());
        return clientDTO;
    }

    @Override
    public BankAccountDTO consulter(String accountId) throws BankAccountNotFoundException {
       BankAccount bankAccount= bankAccountRepository.findById(accountId).
               orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
       if(bankAccount instanceof CurrentAccount){
           CurrentAccount currentAccount=(CurrentAccount) bankAccount;
           return bankAccountMapper.fromCurrentAccount(currentAccount);
       }else{
           SavingAccount savingAccount= (SavingAccount) bankAccount;
           return bankAccountMapper.fromSavingAccount(savingAccount);
       }

    }

    @Override
    public void debit(String accountId, double montant, String description) throws BankAccountNotFoundException, soldeNotSufficentException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getSolde()<montant)
            throw new soldeNotSufficentException("Solde insuffisant");
        AccountOperation accountOperation= new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(montant);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setSolde(bankAccount.getSolde()-montant);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double montant, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));

        AccountOperation accountOperation= new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(montant);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setSolde(bankAccount.getSolde()+montant);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, soldeNotSufficentException {
        debit(accountIdSource,amount,"Transfert to"+ accountIdDestination);
        credit(accountIdDestination,amount,"transfert from "+accountIdSource);
    }
    @Override
    public List<BankAccountDTO> bankAccount_LIST() {
       List<BankAccount>bankAccounts=bankAccountRepository.findAll();
        System.out.println(bankAccounts);
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);

            }
        }).collect(Collectors.toList());
        return bankAccountDTOS ;
    }
    @Override
    public ClientDTO getClient(Long client_id) throws ClientNotFoundException {
        Client client = clientRepository.findById(client_id).orElseThrow(() -> new ClientNotFoundException("client Not Found"));
        return bankAccountMapper.fromClient(client);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        log.info("saving new Account ");
        Client client=bankAccountMapper.fromClientDTO(clientDTO);
        Client client_Enregistre=clientRepository.save(client);
        return bankAccountMapper.fromClient(client_Enregistre);
    }
    @Override
    public void  deleteclient(Long id){
        clientRepository.deleteById(id);
    }

    @Override
    public List<AccountOperationDTO>accountHistorique(String account_id) {
        List<AccountOperation> accountOperation = accountOperationRepository.findByBankAccountId(account_id);
        return accountOperation.stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());

    }

    @Override
    public AccountHistoryDTO getAccountHistorique(String account_id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(account_id).orElse(null);
        if(bankAccount ==null)throw new BankAccountNotFoundException("Account not found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(account_id, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> collect = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(collect);
        accountHistoryDTO.setAccount_id(bankAccount.getId());
        accountHistoryDTO.setSolde(bankAccount.getSolde());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;

    }

    @Override
    public List<ClientDTO> searchClient(String keyword) {
        List<Client> clientList=clientRepository.findByNameContains(keyword);
        List<ClientDTO> clientDTOS = clientList.stream().map(cust -> bankAccountMapper.fromClient(cust)).collect(Collectors.toList());
        return  clientDTOS;
    }

}
