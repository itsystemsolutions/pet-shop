package com.thesis.petshop.services.exceptions;

/*
 * Created: renzb
 * Date: 8/23/2022
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class ExistingException extends RuntimeException{

    public ExistingException(String message) {
        super(message);
    }
}
