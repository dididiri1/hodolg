package com.hodolog.api.controller;

import com.hodolog.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        /*FieldError fieldError = e.getFieldError();
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();

        Map<String, String> response = new HashMap<>();
        response.put(field, message);
        */

        return new ErrorResponse("400", "잘못된 요청입니다.");
    }
}
