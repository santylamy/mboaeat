package com.mboaeat.account.hateoas.assembler;

import com.mboaeat.account.controller.UserRestController;
import com.mboaeat.common.dto.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements SimpleRepresentationModelAssembler<User> {

    @Override
    public void addLinks(EntityModel<User> resource) {
        resource.add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(UserRestController.class)
                                        .getProfile(resource.getContent().getEmail())
                        ).withSelfRel()

        );
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<User>> resources) {

    }
}
