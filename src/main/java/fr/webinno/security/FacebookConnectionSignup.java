package fr.webinno.security;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import fr.webinno.domain.User;
import fr.webinno.repository.UserRepository;


import fr.webinno.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public String execute(Connection<?> connection) {
        System.out.println("signup === ");
        final User user = userService.getUserByName(connection.getDisplayName());
        if( user != null){
            System.out.println(user);
            return user.getIdUser().toString();
        } else{
            user.setName(connection.getDisplayName());
            user.setPassword(randomAlphabetic(8));
            userRepository.save(user);
            return user.getIdUser().toString();
        }

    }

}