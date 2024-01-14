package com.example.alaa.university.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

@GetMapping
    public String sayHello(){
    return "Hi,hello admin";
}

}
