package com.mboaeat.customer.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mboaeat.common.dto.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

@Getter
@NoArgsConstructor
public class ClientModel extends EntityModel<Client> {

    @JsonCreator
    public ClientModel(final Client client){
        super(client);
    }
}
