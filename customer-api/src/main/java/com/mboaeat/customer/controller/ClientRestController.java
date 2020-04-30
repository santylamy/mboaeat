package com.mboaeat.customer.controller;

import com.mboaeat.common.dto.Client;
import com.mboaeat.common.dto.User;
import com.mboaeat.customer.client.AccountClient;
import com.mboaeat.customer.hateoas.ClientModel;
import com.mboaeat.customer.hateoas.assembler.ClientModelAssembler;
import com.mboaeat.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/clients")
@Tag(name = "Client API", description = "Provides a list of methods that retrieve clients and their data")
@Validated
public class ClientRestController {

    private final AccountClient accountClient;
    private final CustomerService customerService;
    private final ClientModelAssembler clientModelAssembler;

    public ClientRestController(AccountClient accountClient, CustomerService customerService, ClientModelAssembler clientModelAssembler) {
        this.accountClient = accountClient;
        this.customerService = customerService;
        this.clientModelAssembler = clientModelAssembler;
    }

    @Operation(summary = "Register new client by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "No user found")
    })
    @PostMapping("/withUser/{userId}")
    public ClientModel newClient(
            @PathVariable("userId") Long userId,
            @RequestBody @Valid Client client){
        User user = accountClient.findUserById(userId);
        Client newClient = customerService.createCustomer(user, client);

        ClientModel clientModel = clientModelAssembler.toModel(newClient);
        clientModel.add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder.methodOn(ClientRestController.class).getClient(newClient.asLongId())).withSelfRel(),
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder.methodOn(ClientRestController.class).getClientByUser(user.asLongId())).withSelfRel()
        );

        return clientModel;
    }

    public ClientModel updateClient(){

        return null;
    }

    @Operation(summary = "Get client by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "No client found") })
    @GetMapping(value = "/{clientId}")
    public ClientModel getClient(@PathVariable("clientId") Long clientId) {
        Client client = customerService.getClientById(clientId);
        return getClientModel(client);
    }

    @Operation(summary = "Get client by user")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "No client found") })
    @GetMapping("/user/{userId}")
    public ClientModel getClientByUser(@PathVariable("userId") Long userId){
        Client client = customerService.getClientByUser(userId);

        return getClientModel(client);
    }

    private ClientModel getClientModel(Client client) {
        ClientModel clientModel = clientModelAssembler.toModel(client);
        clientModel.add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder.methodOn(ClientRestController.class).getClient(client.asLongId())).withSelfRel()
                ); return clientModel;
    }

}
