package com.mboaeat.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mboaeat.common.advice.RestResponseExceptionHandler;
import com.mboaeat.account.service.AccountExistException;
import com.mboaeat.account.service.AccountNotFoundException;
import com.mboaeat.account.service.AccountService;
import com.mboaeat.account.service.InsufficientPasswordException;
import com.mboaeat.common.dto.ErrorCodeConstants;
import com.mboaeat.common.dto.error.ApiErrorDTO;
import com.mboaeat.common.dto.error.ErrorMessageDTO;
import com.mboaeat.common.dto.request.ChangeEmailUserInfo;
import com.mboaeat.common.dto.request.ChangePasswordDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SettingsRestController.class})
@ContextConfiguration(classes = {RestResponseExceptionHandler.class, SettingsRestController.class})
class SettingsRestControllerTest extends AbstractRestControllerTest{


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @Autowired
    RestResponseExceptionHandler restResponseExceptionHandler;


    @Test
    void updateEmail_thenNo_parameter_thenReturn400() throws Exception {
        mockMvc.perform(
                put("/api/v1.0/users/account/update/detail")
                        .contentType("application/json")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEmail_thenIncorrect_email_parameter_thenReturn400() throws Exception {
        ChangeEmailUserInfo changeEmailUserInfo =
                ChangeEmailUserInfo
                        .builder()
                        .id("1")
                        .email("dupon.jeandomain.com")
                        .firstName("dupon")
                        .lastName("jean")
                        .middleName("dupon.jean")
                        .build();
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1.0/users/account/update/detail")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeEmailUserInfo))
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("email: must be a well-formed email address");
    }

    @Test
    void updateEmail_thenUserNot_exist_thenReturn400() throws Exception {
        Long id = 1L;
        Throwable throwable =  new AccountNotFoundException("Account not exist with id " + id, ErrorCodeConstants.AC1000);
        ChangeEmailUserInfo changeEmailUserInfo =
                ChangeEmailUserInfo
                        .builder()
                        .id(String.valueOf(id))
                        .email("dupon.jean@domain.com")
                        .firstName("dupon")
                        .lastName("jean")
                        .middleName("dupon.jean")
                        .build();
        doThrow(throwable)
                .when(accountService)
                .updateAccount(
                        changeEmailUserInfo.asLongId(),
                        changeEmailUserInfo.getEmail(),
                        changeEmailUserInfo.getFirstName(),
                        changeEmailUserInfo.getLastName(),
                        changeEmailUserInfo.getMiddleName()
                );
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1.0/users/account/update/detail")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeEmailUserInfo))
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("Account not exist with id " + id);
    }

    @Test
    void updateEmail_thenUser_with_email_exist_thenReturn400() throws Exception {
        Long id = 1L;

        ChangeEmailUserInfo changeEmailUserInfo =
                ChangeEmailUserInfo
                        .builder()
                        .id(String.valueOf(id))
                        .email("dj@domain.com")
                        .firstName("dupon")
                        .lastName("jean")
                        .middleName("dupon.jean")
                        .build();
        Throwable throwable =  new AccountExistException("Exist account with email " + changeEmailUserInfo.getEmail(), ErrorCodeConstants.AC1001);
        doThrow(throwable)
                .when(accountService)
                .updateAccount(
                        changeEmailUserInfo.asLongId(),
                        changeEmailUserInfo.getEmail(),
                        changeEmailUserInfo.getFirstName(),
                        changeEmailUserInfo.getLastName(),
                        changeEmailUserInfo.getMiddleName()
                );
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1.0/users/account/update/detail")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeEmailUserInfo))
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorMessageDTO errorMessageDTO = apiErrorDTO.getErrorMessages().iterator().next();
        assertThat(errorMessageDTO.getMessage()).isEqualTo("Exist account with email " + changeEmailUserInfo.getEmail());
        assertThat(errorMessageDTO.getCode()).isEqualTo(ErrorCodeConstants.AC1001);
    }



    @Test
    void updateEmail() throws Exception {
        ChangeEmailUserInfo changeEmailUserInfo =
                ChangeEmailUserInfo
                        .builder()
                        .id("1")
                        .email("dupon.jean@domain.com")
                        .firstName("dupon")
                        .lastName("jean")
                        .middleName("dupon.jean")
                        .build();
        mockMvc.perform(
                put("/api/v1.0/users/account/update/detail")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeEmailUserInfo))
        )
                .andExpect(status().isNoContent());
    }


    @Test
    void updatePassword_thenNo_parameter_thenReturn400() throws Exception {
        mockMvc.perform(
                put("/api/v1.0/users/account/update/password")
                        .contentType("application/json")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePassword() throws Exception {
        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO
                .builder()
                .id("1")
                .oldPassword("password")
                .newPassword("password1")
                .confirmPassword("password1")
                .build();
        mockMvc.perform(
                put("/api/v1.0/users/account/update/password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePassword_thenNo_confirmPassword_ThenReturn400() throws Exception {
        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO
                .builder()
                .id("1")
                .oldPassword("password")
                .newPassword("password1")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1.0/users/account/update/password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO))
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorMessageDTO errorMessageDTO = apiErrorDTO.getErrorMessages().iterator().next();
        assertThat(errorMessageDTO.getMessage()).isEqualTo("confirmPassword: must not be blank");
    }



    @Test
    void updatePassword_thenNo_InsufficientPassword_ThenReturn400() throws Exception {
        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO
                .builder()
                .id("1")
                .oldPassword("password")
                .newPassword("password1")
                .confirmPassword("password1")
                .build();

        Throwable throwable =  new InsufficientPasswordException("Account password not sufficiently trusted");
        doThrow(throwable)
                .when(accountService)
                .updatePassword(
                        changePasswordDTO.asLongId(),
                        changePasswordDTO.getOldPassword(),
                        changePasswordDTO.getNewPassword(),
                        changePasswordDTO.getConfirmPassword()
                );

        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1.0/users/account/update/password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO))
        )
                .andReturn();

        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorMessageDTO errorMessageDTO = apiErrorDTO.getErrorMessages().iterator().next();
        assertThat(errorMessageDTO.getMessage()).isEqualTo("Account password not sufficiently trusted");
    }
}