package org.sid.ebanking.service;

import org.sid.ebanking.entities.BankAccount;
import org.sid.ebanking.entities.CurrentAccount;
import org.sid.ebanking.entities.SavingAccount;
import org.sid.ebanking.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void  consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("4fa33c24-49fd-4aed-b35f-ad2e167c414a").get();
        System.out.println("*****************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getSolde());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(bankAccount.getClient().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if (bankAccount instanceof CurrentAccount) {
            System.out.println("OverDraft"+((CurrentAccount) bankAccount).getOverDraft());
        } else if (bankAccount instanceof SavingAccount) {
            System.out.println("Rate"+((SavingAccount) bankAccount).getInterestRate());
        }
        bankAccount.getAccountOperations().forEach(accountOperation -> {
            System.out.println("===============================");
            System.out.println(accountOperation.getType());
            System.out.println(accountOperation.getAmount());
            System.out.println(accountOperation.getOperationDate());
        });
    }
}
