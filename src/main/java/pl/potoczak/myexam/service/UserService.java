package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.potoczak.myexam.model.Role;
import pl.potoczak.myexam.model.User;
import pl.potoczak.myexam.repository.RoleRepository;
import pl.potoczak.myexam.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public boolean isEmailExists(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public void validateAddUser(User user, BindingResult result) {
        if (!user.getPassword().equals(user.getMatchPassword())) {
            result.rejectValue("matchPassword", "error.matchPassword", "Password don't match.");
        }

        if (isUsernameExists(user.getUsername())) {
            result.rejectValue("username", "error.username", "There is already a user with this username!");
        }

        if (isEmailExists(user.getEmail())) {
            result.rejectValue("email", "error.email", "There is already a user with this e-mail!");
        }
    }

    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
