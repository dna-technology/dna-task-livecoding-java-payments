package com.digitalnewagency.task.service;

import com.digitalnewagency.task.api.dto.UserDto;
import com.digitalnewagency.task.persistence.entity.User;
import com.digitalnewagency.task.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Transactional
    public UserDto addUser(String fullName, String email) {
        User user = userRepository.save(new User(UUID.randomUUID(), fullName, email));
        accountService.addAccountForUser(user.getUserId());

        return userToUserDto(user);
    }

    public UserDto getUser(UUID userId) {
        return userRepository.findOneByUserId(userId).map(this::userToUserDto).orElseThrow();
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(user.getUserId(), user.getFullName(), user.getEmail());
    }
}
