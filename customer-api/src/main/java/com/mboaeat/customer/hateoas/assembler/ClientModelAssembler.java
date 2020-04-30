package com.mboaeat.customer.hateoas.assembler;

import com.mboaeat.common.dto.Client;
import com.mboaeat.customer.controller.ClientRestController;
import com.mboaeat.customer.hateoas.ClientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ClientModelAssembler extends RepresentationModelAssemblerSupport<Client, ClientModel> {

    public ClientModelAssembler() {
        super(ClientRestController.class, ClientModel.class);
    }

    @Override
    public ClientModel toModel(Client entity) {
        return new ClientModel(entity);
    }
}
