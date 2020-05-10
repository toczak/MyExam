package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.dto.UserDto;
import pl.potoczak.myexam.model.User;
import pl.potoczak.myexam.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("pageTitle", "Main page | Admin | MyExam");
        return "admin/index";
    }

    @GetMapping(value = {"/settings"})
    public String getSettingsPage(Model model) {
        model.addAttribute("pageTitle", "Settings | Admin | MyExam");
        return "admin/settings";
    }

    @GetMapping(value = {"/user/all"})
    public String getUsersList(Model model) {
        model.addAttribute("pageTitle", "Users list | Admin | MyExam");
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }

    @GetMapping(value = {"/user/add"})
    public String getAddUserForm(Model model, @ModelAttribute("user") UserDto user) {
        model.addAttribute("pageTitle", "Add user | Admin | MyExam");
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/user-add";
    }

    @PostMapping(value = {"/user/add"})
    public String addNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        userService.validateAddUser(userDto, result);

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add user | Admin | MyExam");
            model.addAttribute("roles", userService.getAllRoles());
            return "admin/user-add";
        }
        User user = userService.getNewUserFromDTO(userDto);
        userService.addUser(user);
        return getUsersList(model);
    }

    @GetMapping(value = {"/user/edit/{id}"})
    public String getEditUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:" + getUsersList(model);
        } else {
            UserDto userDto = userService.getUserDTO(user);
            model.addAttribute("pageTitle", "Edit user (id: " + id + ") | Admin | MyExam");
            model.addAttribute("user", userDto);
            model.addAttribute("roles", userService.getAllRoles());
            return "admin/user-edit";
        }
    }

    @PostMapping(value = "/user/edit/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") @Valid UserDto userDto,
                           BindingResult result, Model model) {
        userService.validateEditUser(userDto, result);
        if (result.hasErrors()) {
            userDto.setId(id);
            model.addAttribute("pageTitle", "Edit user (id: " + id + ") | Admin | MyExam");
            model.addAttribute("roles", userService.getAllRoles());
            return "admin/user-edit";
        }
        User user = userService.getEditedUserFromDTO(userDto);
        userService.saveUser(user);
        return getUsersList(model);
    }

    @PostMapping(value = "/user/delete")
    public String deleteUser(@RequestParam (name = "id_del") long id, Model model) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return getUsersList(model);
    }
}
