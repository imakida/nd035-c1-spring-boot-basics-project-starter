package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;

@Service
public class UserService {
    private final HashService hashService;
    private final UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        if (getUserByUsername(user.getUsername()) != null) {
            return -1;
        }
        else {
            return userMapper.insert(new User(
                    null,
                    user.getUsername(),
                    encodedSalt,
                    hashedPassword,
                    user.getFirstName(),
                    user.getLastName()
                    )
            );
        }
    }

    public User getUserByUsername(String username){
        return userMapper.getUser(username);
    }
}
