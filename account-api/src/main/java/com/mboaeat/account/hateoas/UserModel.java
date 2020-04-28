package com.mboaeat.account.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mboaeat.common.dto.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

@Getter
@NoArgsConstructor
public class UserModel extends EntityModel<User> {

    @JsonCreator
    public UserModel(final User user){
        super(user);
    }
}
