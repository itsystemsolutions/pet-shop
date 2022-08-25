package com.thesis.petshop.services.utils;

/*
 * Created: renzb
 * Date: 8/17/2022
 */

public class Response {

    private String statusCode;
    private String message;

    public Response() { }

    public Response(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static Response ok() {
        return new Response("OK","Process Success!");
    }

    public static Response failed(String message) {
        return new Response("NOK",message);
    }

    public static Response notExists() {
        return new Response("NOK", "Record did not exists");
    }

    public static Response successMessage(String message) {
        return new Response("OK","Process Success! " + message);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
