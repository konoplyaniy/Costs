package com.purchases.my.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(Exception e) {
        System.out.println("404 handling  :  " + e.getMessage());
        return "404";
    }

//    @RequestMapping(value = {"/404"}, method = RequestMethod.GET)
//    public String pageNotFound() {
//        return "404";
//    }


}
