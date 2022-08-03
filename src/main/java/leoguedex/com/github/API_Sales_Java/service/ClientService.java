package leoguedex.com.github.API_Sales_Java.service;

import leoguedex.com.github.API_Sales_Java.model.Client;
import leoguedex.com.github.API_Sales_Java.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private static final String CLIENT_NOT_FOUND = "Client not found";

    public Client includeClient(Client client) {
        return clientRepository.save(client);
    }

    public void updateClient(Client client) {
        clientRepository.findById(client.getId())
                .map(clientFound -> {
                    client.setId(clientFound.getId());
                    clientRepository.save(client);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public void deleteClient(Integer id) {
        clientRepository.findById(id)
                .map(clientFound -> {
                    clientRepository.delete(clientFound);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public Client findClientById(Integer id){
        return    clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND));
    }

    public List<Client> findAllClient(){
        return clientRepository.findAll();
    }

    public List<Client> filterClient(Client client){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> clientFiltrado = Example.of(client, exampleMatcher);
        return clientRepository.findAll(clientFiltrado);
    }

}
