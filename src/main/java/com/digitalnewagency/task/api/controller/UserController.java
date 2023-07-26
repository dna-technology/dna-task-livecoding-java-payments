package com.digitalnewagency.task.api.controller;

import com.digitalnewagency.task.api.dto.UserDto;
import com.digitalnewagency.task.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.digitalnewagency.task.api.controller.UserController.PATH;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(PATH)
public class UserController {

    public static final String PATH = "/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto.getFullName(), userDto.getEmail());
    }
}
