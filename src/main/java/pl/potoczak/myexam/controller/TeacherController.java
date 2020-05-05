package pl.potoczak.myexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher/")
public class TeacherController {

    @GetMapping(value = {"","/", "/index"})
    public String showIndexPage(Model model) {
        return "teacher/index";
    }
}
