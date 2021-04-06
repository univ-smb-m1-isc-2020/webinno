package fr.webinno.service;

import fr.webinno.domain.User;
import fr.webinno.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public Optional<User> getUserById(long idUser) {
        return userRepository.findById(idUser);
    }

    @PostConstruct
    public void initialize(){
        if(userRepository.findAll().isEmpty()){
            userRepository.saveAndFlush(new User("Kistchminyof", "Stephan", "stephank51@gmail.com", "MyPassword123"));
            userRepository.saveAndFlush(new User("Gallet", "Benjamin", "benjamin.gallet@gmail.com", "123456789"));
        }
    }
}
