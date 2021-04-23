package fr.webinno.service;

import fr.webinno.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(long idUser);
    User getUserByName(String name); 
}
