package org.sid.ebanking;

import org.sid.ebanking.Exception.BankAccountNotFoundException;
import org.sid.ebanking.Exception.ClientNotFoundException;
import org.sid.ebanking.Exception.soldeNotSufficentException;
import org.sid.ebanking.dtos.BankAccountDTO;
import org.sid.ebanking.dtos.ClientDTO;
import org.sid.ebanking.dtos.CurrentBankAccountDTO;
import org.sid.ebanking.dtos.SavingBankAccountDTO;
import org.sid.ebanking.entities.*;
import org.sid.ebanking.enums.AccountStatus;
import org.sid.ebanking.enums.OperationType;
import org.sid.ebanking.repositories.AccountOperationRepository;
import org.sid.ebanking.repositories.BankAccountRepository;
import org.sid.ebanking.repositories.ClientRepository;
import org.sid.ebanking.service.BankAccountService;
import org.sid.ebanking.service.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {

        SpringApplication.run(EBankingApplication.class, args);
    }
    @Bean
    CommandLineRunner start2(BankAccountService bankAccountService){
        return args -> {
          Stream.of("manal","amine","Mohemed").forEach(name->{
              ClientDTO client =new ClientDTO();
              client.setName(name);
              client.setEmail(name+"@gmail.com");
              bankAccountService.saveClient(client);
          });
          bankAccountService.CLIENT_LIST().forEach(client->{
              try {
                  bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,client.getId());
                  bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, client.getId());

              } catch (ClientNotFoundException e) {
                  e.printStackTrace();
              }
          }
          );
            List<BankAccountDTO>bankAccounts=bankAccountService.bankAccount_LIST();
            for(BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i < 5; i++) {
                    String account_id;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        account_id=((SavingBankAccountDTO)bankAccount).getId();
                    }else{
                        account_id=((CurrentBankAccountDTO)bankAccount).getId();
                    }
                    bankAccountService.credit(account_id, 10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(account_id, 1000+Math.random()*9000,"Debit");
                }
            }
        };
        }
    //@Bean
    CommandLineRunner start(ClientRepository clientRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
                Client client = new Client();
                client.setName(name);
                client.setEmail(name + "@gmail.com");
                clientRepository.save(client);
            });
            clientRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setSolde(Math.random() * 9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setClient(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setSolde(Math.random() * 9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setClient(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });


        };

    }
}



