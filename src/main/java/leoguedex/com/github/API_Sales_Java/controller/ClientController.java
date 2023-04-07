package leoguedex.com.github.API_Sales_Java.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import leoguedex.com.github.API_Sales_Java.model.Client;
import leoguedex.com.github.API_Sales_Java.model.dto.ClientDTO;
import leoguedex.com.github.API_Sales_Java.service.ClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Client API")
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client save with successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 401, message = "User don't have authorization"),
            @ApiResponse(code = 403, message = "User without access right")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Client registerClient(@RequestBody @Valid Client client) {
        return clientService.includeClient(client);
    }     

    @PutMapping("/{id}")
    @ApiOperation(value = "Update data in client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client update with successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 401, message = "User don't have authorization"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public void updateClient(@PathVariable Integer id, @RequestBody @Valid ClientDTO clientDTO) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<ClientDTO, Client> typeMap = mapper.createTypeMap(ClientDTO.class, Client.class);
        typeMap.addMappings(m -> m.map(ClientDTO::getName, Client::setName));
        typeMap.addMappings(m -> m.map(ClientDTO::getCpf, Client::setCpf));
        Client client = mapper.map(clientDTO, Client.class);
        client.setId(id);
        clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a existent Client")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Client deleted with successfully"),
            @ApiResponse(code = 401, message = "User don't have authorization"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find client by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client locate with successfully"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public Client findClientById(@PathVariable Integer id){
        return clientService.findClientById(id);
    }

    @GetMapping()
    @ApiOperation(value = "Find all clients")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Users not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Client> findAllClient(){
        return clientService.findAllClient();
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Filter client by name")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> filterClient(Client client){
        return clientService.filterClient(client);
    }

}
