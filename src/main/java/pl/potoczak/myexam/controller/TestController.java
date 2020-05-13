package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.model.*;
import pl.potoczak.myexam.service.TestService;

import javax.validation.Valid;

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
        model.addAttribute("tests", testService.getAllLoggedInTeacherTests());
        return "teacher/test-list";
    }

    @GetMapping(value = {"/test/add"})
    public String getQuestionAddForm(Model model, @ModelAttribute("test") Test test) {
        model.addAttribute("pageTitle", "Add test | Teacher | MyExam");
        model.addAttribute("questionsList", testService.getAllLoggedInTeacherQuestions());
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
        Test test = testService.getLoggedInTeacherTestById(id);
        if (test == null) {
            return "redirect:/teacher/test/all";
        } else {
            model.addAttribute("pageTitle", "Edit test (id: " + id + ") | Teacher | MyExam");
            model.addAttribute("test", test);
            model.addAttribute("questionsList", testService.getAllLoggedInTeacherQuestions());
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
            model.addAttribute("questionsList", testService.getAllLoggedInTeacherQuestions());
            model.addAttribute("studentsList", testService.getAllStudents());
            return "teacher/test-edit";
        }
        testService.editTest(test);
        return "redirect:/teacher/test/all";
    }

    @PostMapping(value = "/test/delete")
    public String deleteUser(@RequestParam(name = "id_del") long id, Model model) {
        Test test = testService.getLoggedInTeacherTestById(id);
        if (test != null)
            testService.deleteTest(test);
        return "redirect:/teacher/test/all";
    }

    @GetMapping(value = {"/test/{id}"})
    public String getStudentsTestResults(@PathVariable("id") long id, Model model) {
        Test test = testService.getLoggedInTeacherTestById(id);
        if (test == null) {
            return "redirect:/teacher/test/all";
        } else {
            model.addAttribute("pageTitle", "Test results (test id: " + id + ") | Teacher | MyExam");
            model.addAttribute("testResults", testService.getAllTestResultsByTest(id));
            return "teacher/test-result-list";
        }
    }

    @GetMapping(value = {"/test/{id_test}/result/{id_test_result}"})
    public String getStudentsTestResults(@PathVariable("id_test") long idTest, @PathVariable("id_test_result") long idTestResult, Model model) {
        Test test = testService.getLoggedInTeacherTestById(idTest);
        if (test == null) {
            return "redirect:/teacher/test/all";
        }

        TestResult testResult = testService.getTestResultById(idTestResult);
        if (testResult == null) {
            return "redirect:/teacher/test/all";
        }
        model.addAttribute("pageTitle", "Test result (" + testResult.getStudent().getFullName() + ") | Teacher | MyExam");
        model.addAttribute("testResult", testResult);
        return "teacher/test-result";
    }
}
