package com.example.demo.controllers;

import com.example.demo.mysql.RegistrationRequest;
import com.example.demo.mysql.Response;
import com.example.demo.mysql.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/register")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> registerUser(@RequestBody RegistrationRequest userRegistrationRequest) {
        userService.registerUser(userRegistrationRequest);

        Response response = new Response("User registered successfully");

        return ResponseEntity.ok(response);
    }
}

