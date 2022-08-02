package org.sid.ebanking.repositories;

import org.sid.ebanking.entities.AccountOperation;
import org.sid.ebanking.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
     List<AccountOperation>findByBankAccountId(String account_id);
     Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String account_id, Pageable pageable);

}
