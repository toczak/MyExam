package pl.potoczak.myexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.potoczak.myexam.dto.QuestionDto;
import pl.potoczak.myexam.model.Answer;
import pl.potoczak.myexam.model.Question;
import pl.potoczak.myexam.service.QuestionService;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/all")
    public String getAllUserQuestions(Model model) {
        model.addAttribute("pageTitle", "Questions list | Teacher | MyExam");
        model.addAttribute("questions", questionService.getAllTeacherQuestions());
        return "teacher/question-list";
    }

    @GetMapping(value = {"/question/add"})
    public String getQuestionAddForm(Model model, @ModelAttribute("question") QuestionDto question) {
        model.addAttribute("pageTitle", "Add question | Teacher | MyExam");
        for (int i = 0; i < 4; i++) {
            question.addAnswer(new Answer());
        }
        return "teacher/question-add";
    }

    @PostMapping(value = {"/question/add"})
    public String addNewQuestion(@ModelAttribute("question") @Valid QuestionDto questionDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add question | Teacher | MyExam");
            return "teacher/question-add";
        }
        Question question = questionService.getNewQuestionFromDto(questionDto);
        questionService.saveQuestion(question);
        return "redirect:/teacher/question/all";
    }

    @GetMapping(value = {"/question/edit/{id}"})
    public String getEditUserForm(@PathVariable("id") long id, Model model) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            return "redirect:/teacher/question/all";
        } else {
            QuestionDto questionDto = questionService.getQuestionDto(question);
            model.addAttribute("pageTitle", "Edit question (id: " + id + ") | Admin | MyExam");
            model.addAttribute("question", questionDto);
            return "teacher/question-edit";
        }
    }

    @PostMapping(value = "/question/edit/{id}")
    public String editQuestion(@PathVariable("id") long id, @ModelAttribute("question") @Valid QuestionDto questionDto,
                           BindingResult result, Model model) {
        questionDto.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit question (id: " + id + ") | Teacher | MyExam");
            return "teacher/question-edit";
        }
        Question question = questionService.getEditedQuestionFromDTO(questionDto);
        questionService.saveQuestion(question);
        return "redirect:/teacher/question/all";
    }

    @PostMapping(value = "/question/delete")
    public String deleteUser(@RequestParam(name = "id_del") long id, Model model) {
        Question question = questionService.getQuestionById(id);
        if (question != null)
            questionService.deleteQuestion(question);
        return "redirect:/teacher/question/all";
    }
}
