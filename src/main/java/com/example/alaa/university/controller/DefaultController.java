package com.example.alaa.university.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/default")
public class DefaultController {
    @GetMapping
    public String sayHello(){
        return "Hi,hello default";
    }
}

