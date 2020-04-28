package com.mboaeat.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mboaeat.account.controller.advice.RestResponseExceptionHandler;
import com.mboaeat.account.hateoas.UserModel;
import com.mboaeat.account.hateoas.assembler.UserModelAssembler;
import com.mboaeat.account.service.AccountNotFoundException;
import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.dto.ErrorCodeConstants;
import com.mboaeat.common.dto.error.ApiErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = {UserRestController.class})
@ContextConfiguration(classes = {UserModelAssembler.class, RestResponseExceptionHandler.class, UserRestController.class})
class UserRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @Autowired
    RestResponseExceptionHandler restResponseExceptionHandler;

    @Autowired
    UserModelAssembler userModelAssembler;

    @Test
    public void getProfile_throw_Exception() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/users/dupon.jeangmail.com")
                        .contentType("application/json")
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("must be a well-formed email address");
    }

    @Test
    public void getProfile_throw_NotFound_Profile_throwException() throws Exception {
        String email = "dj@gmail.com";
        Throwable throwable = new AccountNotFoundException("No account found with email " + email, ErrorCodeConstants.AC1000);
        doThrow(throwable).when(accountService).getUserByEmail(email);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/users/dj@gmail.com")
                        .contentType("application/json")
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("No account found with email " + email);
    }


    @Test
    public void getProfile() throws Exception {
        when(accountService.getUserByEmail(user.getEmail())).thenReturn(user);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/users/dupon.jean@gmail.com")
                        .contentType("application/json")
        )
                .andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        UserModel entityModel = objectMapper.readValue(expectedResponseBody,UserModel.class);
        assertThat(entityModel).isNotNull();
        assertThat(entityModel.getContent().getEmail().equalsIgnoreCase(user.getEmail()));
    }

}