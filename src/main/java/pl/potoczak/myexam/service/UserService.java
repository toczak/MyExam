package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.potoczak.myexam.dto.UserDTO;
import pl.potoczak.myexam.model.Role;
import pl.potoczak.myexam.model.User;
import pl.potoczak.myexam.repository.RoleRepository;
import pl.potoczak.myexam.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public boolean isEmailExists(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public void validateAddUser(UserDTO user, BindingResult result) {
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

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setRole(user.getRole());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User getNewUserFromDTO(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public User getEditedUserFromDTO(UserDTO userDto) {
        User user = userRepository.findUserById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().equals("") && !userDto.getPassword().isEmpty()) {
            if (!passwordEncoder.encode(userDto.getPassword()).equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
        }
        return user;
    }

    public void validateEditUser(UserDTO user, BindingResult result) {
        if (!user.getPassword().equals(user.getMatchPassword())) {
            result.rejectValue("matchPassword", "error.matchPassword", "Password don't match.");
        }

        if (isUserWithUsernameWithoutIdExists(user.getUsername(), user.getId())) {
            result.rejectValue("username", "error.username", "There is already a user with this username!");
        }

        if (isUserWithEmailWithoutIdExists(user.getEmail(), user.getId())) {
            result.rejectValue("email", "error.email", "There is already a user with this e-mail!");
        }
    }

    private boolean isUserWithUsernameWithoutIdExists(String username, Long id) {
        return userRepository.findUserByUsernameAndIdNotLike(username, id) != null;
    }

    private boolean isUserWithEmailWithoutIdExists(String email, Long id) {
        return userRepository.findUserByEmailAndIdNotLike(email, id) != null;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Iterable<User> getAllStudents() {
        return userRepository.findAllByRole_NameEquals("ROLE_STUDENT");
    }

    public Role getStudentRole(){
        return roleRepository.getRoleByName("ROLE_STUDENT");
    }


}
