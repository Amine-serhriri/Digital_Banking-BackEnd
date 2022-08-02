package org.sid.ebanking.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking.Exception.ClientNotFoundException;
import org.sid.ebanking.dtos.ClientDTO;
import org.sid.ebanking.dtos.DebitDTO;
import org.sid.ebanking.entities.Client;
import org.sid.ebanking.repositories.ClientRepository;
import org.sid.ebanking.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j
public class ClientRestController {
    private BankAccountService bankAccountService;
    private ClientRepository clientRepository;

    @GetMapping("/clients" )
    List<ClientDTO> clients(){
        return bankAccountService.CLIENT_LIST();
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable(name = "id") Long client_id) throws ClientNotFoundException {
       return  bankAccountService.getClient(client_id);

    }
    @PostMapping("/clients")
    public ClientDTO saveClient(@RequestBody  ClientDTO clientdto){
        return bankAccountService.saveClient(clientdto);
    }

    @PutMapping ("/clients/{client_id}")
    public ClientDTO updateClient(@RequestBody ClientDTO clientdto, @PathVariable Long client_id){
        clientdto.setId(client_id);
        return bankAccountService.updateClient(clientdto);
    }
    @DeleteMapping("clients/{client_id}")
    public void deleteClient(@PathVariable(name ="client_id" )Long id){
        bankAccountService.deleteclient(id);

    }
    @GetMapping("/clients/search")
    List<ClientDTO> searchclients(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchClient( keyword);
    }

}
