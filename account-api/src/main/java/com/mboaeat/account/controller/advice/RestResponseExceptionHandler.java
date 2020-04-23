package com.mboaeat.account.controller.advice;

import com.mboaeat.common.dto.error.ApiErrorDTO;
import com.mboaeat.common.dto.error.ErrorMessageDTO;
import com.mboaeat.common.exception.MboaEatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param ex {@link com.mboaeat.common.exception.MboaEatException}
     * @return Returns an error status message to the customer
     */
    @ExceptionHandler(MboaEatException.class)
    public ResponseEntity<Object> handleResourceException(final MboaEatException ex) {
        return new ResponseEntity<Object>(
                new ApiErrorDTO(
                        HttpStatus.BAD_REQUEST,
                        ex.getLocalizedMessage(),
                        ErrorMessageDTO
                                .builder()
                                .message(ex.getMessage())
                                .code(ex.getErrorCode())
                                .build()
                ),
                new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ErrorMessageDTO> errors = new ArrayList<ErrorMessageDTO>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    ErrorMessageDTO.builder()
                            .message(violation.getMessage())
                            .build()
                   );
        }

        ApiErrorDTO apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}
