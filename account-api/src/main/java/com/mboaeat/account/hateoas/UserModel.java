package com.mboaeat.account.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mboaeat.common.dto.User;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

@Getter
public class UserModel extends EntityModel<User> {

    @JsonCreator
    public UserModel(final User user){
        super(user);
    }
}
