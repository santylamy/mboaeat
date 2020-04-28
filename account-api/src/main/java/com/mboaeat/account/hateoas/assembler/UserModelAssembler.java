package com.mboaeat.account.hateoas.assembler;

import com.mboaeat.account.controller.UserRestController;
import com.mboaeat.account.hateoas.UserModel;
import com.mboaeat.common.dto.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    public UserModelAssembler(){
        super(UserRestController.class, UserModel.class);
    }


    @Override
    public UserModel toModel(User entity) {
        return new UserModel(entity);
    }

   /* @Override
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

    */
}
