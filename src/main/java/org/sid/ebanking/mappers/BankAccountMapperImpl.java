package org.sid.ebanking.mappers;

import org.sid.ebanking.dtos.AccountOperationDTO;
import org.sid.ebanking.dtos.ClientDTO;
import org.sid.ebanking.dtos.CurrentBankAccountDTO;
import org.sid.ebanking.dtos.SavingBankAccountDTO;
import org.sid.ebanking.entities.AccountOperation;
import org.sid.ebanking.entities.Client;
import org.sid.ebanking.entities.CurrentAccount;
import org.sid.ebanking.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public ClientDTO fromClient(Client client){
        ClientDTO clientDTO=new ClientDTO();

        BeanUtils.copyProperties(client,clientDTO);//remplacer les set et les get

        //clientDTO.setId(client.getId());
        //clientDTO.setName(client.getName());
        //clientDTO.setEmail(client.getEmail());
        return clientDTO;
    }
    public Client fromClientDTO(ClientDTO clientDTO){
        Client client=new Client();
        BeanUtils.copyProperties(clientDTO,client);
        return client;
    }

    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setClientDTO(fromClient(savingAccount.getClient()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO bankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(bankAccountDTO,savingAccount);
        savingAccount.setClient(fromClientDTO(bankAccountDTO.getClientDTO()));

        return savingAccount;
    }

    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setClientDTO(fromClient(currentAccount.getClient()));
        currentBankAccountDTO.setOverdraft(currentAccount.getOverDraft());
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setClient(fromClientDTO(currentAccountDTO.getClientDTO()));
        return currentAccount;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
    public AccountOperation fromAccountOperation(AccountOperationDTO accountOperationDTO){
        return null;
    }

}
