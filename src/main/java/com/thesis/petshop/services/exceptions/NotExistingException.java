package com.thesis.petshop.services.exceptions;

/*
 * Created: renzb
 * Date: 8/23/2022
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotExistingException extends RuntimeException{

    public NotExistingException(String message) {
        super(message);
    }

}
