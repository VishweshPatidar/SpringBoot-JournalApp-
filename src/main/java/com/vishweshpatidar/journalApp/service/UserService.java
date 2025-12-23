package com.vishweshpatidar.journalApp.service;

import com.vishweshpatidar.journalApp.entity.User;
import com.vishweshpatidar.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            log.error("Error occurred for {}: ",user.getUserName(), e);
            log.error("brouhaha");
            log.warn("brouhaha");
            log.info("brouhaha");
            log.debug("brouhaha");
            log.trace("brouhaha");
            return false;
        }

    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myId){
        return userRepository.findById(myId);
    }

    public void deleteById(ObjectId myId){
        userRepository.deleteById(myId);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
