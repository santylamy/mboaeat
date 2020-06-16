package com.mboaeat.common.advice;

import com.mboaeat.common.dto.error.ApiErrorDTO;
import com.mboaeat.common.dto.error.ErrorMessageDTO;
import com.mboaeat.common.exception.MboaEatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param ex {@link com.mboaeat.common.exception.MboaEatException}
     * @return Returns an error status message to the customer
     */
    @ExceptionHandler(MboaEatException.class)
    public ResponseEntity<Object> handleResourceException(final MboaEatException ex) {
        return new ResponseEntity<>(
                new ApiErrorDTO(
                        HttpStatus.BAD_REQUEST,
                        ex.getLocalizedMessage(),
                        ErrorMessageDTO
                                .builder()
                                .message(ex.getMessage())
                                .code(ex.getCode())
                                .build()
                ),
                new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorMessageDTO> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(ErrorMessageDTO.builder().message(error.getField() + ": " + error.getDefaultMessage()).build() );
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add( ErrorMessageDTO.builder().message( error.getObjectName() + ": " + error.getDefaultMessage()).build());
        }

        ApiErrorDTO apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ErrorMessageDTO> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    ErrorMessageDTO.builder()
                            .message(violation.getMessage())
                            .build()
                   );
        }

        ApiErrorDTO apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}
