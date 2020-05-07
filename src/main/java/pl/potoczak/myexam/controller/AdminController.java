package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String getAddUserForm(Model model, User user) {
        model.addAttribute("pageTitle", "Add user | Admin | MyExam");
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/user-add";
    }

    @PostMapping(value = {"/user/add"})
    public String addNewUser(@Valid User user, BindingResult result, Model model) {
        userService.validateAddUser(user, result);

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add user | Admin | MyExam");
            model.addAttribute("roles", userService.getAllRoles());
            return "admin/user-add";
        }
        userService.addUser(user);
        return getUsersList(model);
    }
}
