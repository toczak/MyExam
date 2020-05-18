package pl.potoczak.myexam.dto;

import pl.potoczak.myexam.model.Role;
import pl.potoczak.myexam.validation.Ignored;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SettingsUserDto {
    private Long id;

    private String password;

    private String matchPassword;

    @NotBlank(message = "E-mail is required!")
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
