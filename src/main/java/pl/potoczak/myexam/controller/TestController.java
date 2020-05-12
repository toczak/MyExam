package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.dto.QuestionDto;
import pl.potoczak.myexam.model.*;
import pl.potoczak.myexam.service.QuestionService;
import pl.potoczak.myexam.service.TestService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test/all")
    public String getAllUserQuestions(Model model) {
        model.addAttribute("pageTitle", "Tests list | Teacher | MyExam");
        model.addAttribute("tests", testService.getAllTeacherTests());
        return "teacher/test-list";
    }

    @GetMapping(value = {"/test/add"})
    public String getQuestionAddForm(Model model, @ModelAttribute("test") Test test) {
        model.addAttribute("pageTitle", "Add test | Teacher | MyExam");
        model.addAttribute("questionsList", testService.getAllTeacherQuestions());
        model.addAttribute("studentsList", testService.getAllStudents());
        return "teacher/test-add";
    }

    @PostMapping(value = {"/test/add"})
    public String addNewTest(@ModelAttribute("test") @Valid Test test, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add test | Teacher | MyExam");
            return "teacher/test-add";
        }
        testService.addTest(test);
        return "redirect:/teacher/test/all";
    }

    @GetMapping(value = {"/test/edit/{id}"})
    public String getEditTestForm(@PathVariable("id") long id, Model model) {
        Test test = testService.getTestById(id);
        if (test == null) {
            return "redirect:/teacher/test/all";
        } else {
            model.addAttribute("pageTitle", "Edit test (id: " + id + ") | Admin | MyExam");
            model.addAttribute("test", test);
            model.addAttribute("questionsList", testService.getAllTeacherQuestions());
            model.addAttribute("studentsList", testService.getAllStudents());
            return "teacher/test-edit";
        }
    }

    @PostMapping(value = "/test/edit/{id}")
    public String editTest(@PathVariable("id") long id, @ModelAttribute("test") @Valid Test test,
                           BindingResult result, Model model) {
        test.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit test (id: " + id + ") | Teacher | MyExam");
            model.addAttribute("questionsList", testService.getAllTeacherQuestions());
            model.addAttribute("studentsList", testService.getAllStudents());
            return "teacher/test-edit";
        }
        testService.editTest(test);
        return "redirect:/teacher/test/all";
    }

    @PostMapping(value = "/test/delete")
    public String deleteUser(@RequestParam(name = "id_del") long id, Model model) {
        Test test = testService.getTestById(id);
        if (test != null)
            testService.deleteTest(test);
        return "redirect:/teacher/test/all";
    }
}
