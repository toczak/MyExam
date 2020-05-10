package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.dto.UserDTO;
import pl.potoczak.myexam.model.User;
import pl.potoczak.myexam.service.UserService;
import pl.potoczak.myexam.validation.Ignored;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class TeacherController implements Ignored {

    private UserService userService;

    @Autowired
    public TeacherController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("pageTitle", "Main page | Teacher | MyExam");
        return "teacher/index";
    }

    @GetMapping(value = {"/settings"})
    public String getSettingsPage(Model model) {
        model.addAttribute("pageTitle", "Settings | Teacher | MyExam");
        return "teacher/settings";
    }

    @GetMapping(value = {"/student/all"})
    public String getStudentList(Model model) {
        model.addAttribute("pageTitle", "Students list | Teacher | MyExam");
        model.addAttribute("users", userService.getAllStudents());
        return "teacher/user-list";
    }

    @GetMapping(value = {"/student/add"})
    public String getAddStudentForm(Model model, @ModelAttribute("user") UserDTO user) {
        model.addAttribute("pageTitle", "Add student | Teacher | MyExam");
        return "teacher/user-add";
    }

    @PostMapping(value = {"/student/add"})
    public String addNewStudent(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model) {
        userDTO.setRole(userService.getStudentRole());
        userService.validateAddUser(userDTO, result);

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add student | Teacher | MyExam");
            return "teacher/user-add";
        }
        User user = userService.getNewUserFromDTO(userDTO);
        userService.addUser(user);
        return getStudentList(model);
    }

    @GetMapping(value = {"/student/edit/{id}"})
    public String getEditUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:" + getStudentList(model);
        } else {
            UserDTO userDTO = userService.getUserDTO(user);
            model.addAttribute("pageTitle", "Edit student (id: " + id + ") | Teacher | MyExam");
            model.addAttribute("user", userDTO);
            return "teacher/user-edit";
        }
    }

    @PostMapping(value = "/student/edit/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") @Valid UserDTO userDTO,
                           BindingResult result, Model model) {
        userService.validateEditUser(userDTO, result);
        if (result.hasErrors()) {
            userDTO.setId(id);
            model.addAttribute("pageTitle", "Edit student (id: " + id + ") | Teacher | MyExam");
            model.addAttribute("roles", userService.getAllRoles());
            return "teacher/user-edit";
        }
        userDTO.setRole(userService.getStudentRole());
        User user = userService.getEditedUserFromDTO(userDTO);
        userService.saveUser(user);
        return getStudentList(model);
    }

    @PostMapping(value = "/student/delete")
    public String deleteUser(@RequestParam(name = "id_del") long id, Model model) {
        User user = userService.getUserById(id);
        if (user != null)
            userService.deleteUser(user);
        return getStudentList(model);
    }
}
