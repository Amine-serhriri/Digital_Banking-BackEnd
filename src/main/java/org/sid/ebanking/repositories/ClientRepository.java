package org.sid.ebanking.repositories;

import org.sid.ebanking.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByNameContains(String keyword);
}
