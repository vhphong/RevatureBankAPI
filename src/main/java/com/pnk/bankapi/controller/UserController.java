package com.pnk.bankapi.controller;

import com.pnk.bankapi.model.User;
import com.pnk.bankapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/RevBankAPI/v2/users")
public class UserController {

    private final UserService userService;

    @Value("${server.port}")
    public int serverPort;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcomeUsers() {
        return "Welcome to the endpoint users/welcome. The application is listening on PORT: " + serverPort;
    }


    /* JSON input
        {
            "name": "Sol"
        }
    */
    @PostMapping("/create")
    public ResponseEntity<User> addBankUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.insertUser(user), HttpStatus.CREATED);
    }

}
