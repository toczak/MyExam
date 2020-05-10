package pl.potoczak.myexam.dto;

import pl.potoczak.myexam.model.Role;
import pl.potoczak.myexam.validation.Ignored;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required!")
    private String username;

    @NotBlank(message = "Full name is required!")
    private String fullName;

    private String password;

    private String matchPassword;

    @NotBlank(message = "E-mail is required!")
    @Email
    private String email;

    @NotNull(groups = Ignored.class, message = "Please choose valid role!")
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchPassword() {
        return matchPassword;
    }

    public void setMatchPassword(String matchPassword) {
        this.matchPassword = matchPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
