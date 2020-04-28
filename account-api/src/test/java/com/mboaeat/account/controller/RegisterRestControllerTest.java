package com.mboaeat.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mboaeat.account.controller.advice.RestResponseExceptionHandler;
import com.mboaeat.account.service.AccountExistException;
import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.dto.error.ApiErrorDTO;
import com.mboaeat.common.dto.request.RegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {RegisterRestController.class})
class RegisterRestControllerTest extends AbstractRestControllerTest{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @Autowired
    RestResponseExceptionHandler restResponseExceptionHandler;

    @Test
    void register_whenValidInput_thenReturns201() throws Exception{
        RegisterDTO registerDTO = RegisterDTO
                .builder()
                .email("mail@domail.com")
                .password("password")
                .build();
        mockMvc.perform(
                post("/api/v1.0/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO))
        )
                .andExpect(status().isCreated());
    }

    @Test
    void register_whenNotValidInput_thenReturns400() throws Exception{
        RegisterDTO registerDTO = RegisterDTO
                .builder()
                .email("maildomain.com")
                .password("password")
                .build();
      MvcResult mvcResult = mockMvc.perform(
                post("/api/v1.0/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(registerDTO))
        )
                .andReturn();
      String expectedResponseBody = mvcResult.getResponse().getContentAsString();
      ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
      assertThat(apiErrorDTO).isNotNull();
      assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("email: must be a well-formed email address");
    }

    @Test
    void register_whenNotValidInput_user_exist_thenReturns400() throws Exception{
        String email = "mail@domain.com";
        String password = "password";
        Throwable throwable =  new AccountExistException("Exist account with email " + email);
        doThrow(throwable).when(accountService).createAccount(email, password);
        RegisterDTO registerDTO = RegisterDTO
                .builder()
                .email("mail@domain.com")
                .password("password")
                .build();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1.0/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(registerDTO))
        )
                .andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ApiErrorDTO apiErrorDTO = objectMapper.readerFor(ApiErrorDTO.class).readValue(expectedResponseBody);
        assertThat(apiErrorDTO).isNotNull();
        assertThat(apiErrorDTO.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiErrorDTO.getErrorMessages().iterator().next().getMessage()).isEqualTo("Exist account with email " + email);
    }

    public class ResponseBodyMatchers {
        private ObjectMapper objectMapper = new ObjectMapper();

        public <T> ResultMatcher containsObjectAsJson(
                Object expectedObject,
                Class<T> targetClass) {
            return mvcResult -> {
                String json = mvcResult.getResponse().getContentAsString();
                T actualObject = objectMapper.readValue(json, targetClass);
                assertThat(expectedObject).isEqualToComparingFieldByField(actualObject);
            };
        }

       /* static ResponseBodyMatchers responseBody() {
            return new ResponseBodyMatchers();
        }

        */
    }
}