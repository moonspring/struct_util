package com.lzq.struct.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
