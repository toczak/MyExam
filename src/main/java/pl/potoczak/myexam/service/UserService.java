package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.potoczak.myexam.dto.SettingsUserDto;
import pl.potoczak.myexam.dto.UserDto;
import pl.potoczak.myexam.model.*;
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

    public Teacher getPrincipalTeacher() {
        return (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void validateAddUser(UserDto user, BindingResult result) {
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

    public UserDto getUserDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setRole(user.getRole());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User getNewUserFromDTO(UserDto userDto) {
        User user = getUserByRole(userDto.getRole());
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    private User getUserByRole(Role role) {
        User user;
        switch (role.getName()) {
            case "ROLE_STUDENT":
                user = new Student();
                break;
            case "ROLE_TEACHER":
                user = new Teacher();
                break;
            case "ROLE_ADMIN":
                user = new Admin();
                break;
            default:
                user = new User();
        }
        return user;
    }

    public User getEditedUserFromDTO(UserDto userDto) {
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

    public User getEditedSettingsFromDTO(SettingsUserDto userDto) {
        User user = getPrincipalTeacher();
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().equals("") && !userDto.getPassword().isEmpty()) {
            if (!passwordEncoder.encode(userDto.getPassword()).equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
        }
        return user;
    }

    public void validateEditUser(UserDto user, BindingResult result) {
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

    public Role getStudentRole() {
        return roleRepository.getRoleByName("ROLE_STUDENT");
    }


    public void validateEditSettings(SettingsUserDto user, BindingResult result) {
        if (!user.getPassword().equals(user.getMatchPassword())) {
            result.rejectValue("matchPassword", "error.matchPassword", "Password don't match.");
        }

        if (isUserWithEmailWithoutIdExists(user.getEmail(), getPrincipalTeacher().getId())) {
            result.rejectValue("email", "error.email", "There is already a user with this e-mail!");
        }
    }
}
