package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.model.TestResult;
import pl.potoczak.myexam.service.TestService;

@Controller
public class StudentController {

    private TestService testService;

    @Autowired
    public StudentController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = {"", "/", "/index"})
    public String showIndexPage(Model model) {
        model.addAttribute("pageTitle", "Main page | Student | MyExam");
        return "student/index";
    }

    @GetMapping(value = {"/settings"})
    public String getSettingsPage(Model model) {
        model.addAttribute("pageTitle", "Settings | Student | MyExam");
        return "student/settings";
    }

    @GetMapping(value = {"/test/all"})
    public String getTestList(Model model) {
        model.addAttribute("pageTitle", "Tests list | Student | MyExam");
        model.addAttribute("testList", testService.getAllLoggedInStudentTestResults());
        return "student/test-list";
    }

    @GetMapping(value = {"/test/{id}"})
    public String getTest(@PathVariable("id") long id, Model model) {
        TestResult testResult = testService.getTestResultByLoggedInUser(id);
        if (testResult == null || !testResult.isEnabled()) {
            return "redirect:/test/all";
        }
        testService.shuffleQuestionsAndAnswers(testResult);
        model.addAttribute("pageTitle", "Test - " + testResult.getTest().getName() + " | Student | MyExam");
        model.addAttribute("testResult", testResult);
        return "student/test";
    }

    @PostMapping(value = "/test/{id}")
    public String sendTest(@PathVariable("id") long id, @ModelAttribute("testResult") TestResult testResult,
                           BindingResult result, Model model) {
        testService.fillQuestionsInTestResultAndCheckTest(id, testResult.getAnswerList());
        return "redirect:/test/" + id + "/result";
    }

    @GetMapping(value = "/test/{id}/result")
    public String getTestResult(@PathVariable("id") long id, Model model) {
        TestResult testResult = testService.getTestResultByLoggedInUser(id);
        if (testResult == null || testResult.isEnabled()) {
            return "redirect:/test/all";
        }
        model.addAttribute("pageTitle", "Result test - " + testResult.getTest().getName() + " | Student | MyExam");
        model.addAttribute("testResult", testResult);
        return "student/test-result";
    }
}
