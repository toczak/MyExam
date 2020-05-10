package pl.potoczak.myexam.dto;

import org.hibernate.validator.constraints.Range;
import pl.potoczak.myexam.model.Answer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    private Long id;

    @NotBlank(message = "Text question is required")
    private String text;

    @Valid
    private List<Answer> answers;

    @Range(min = 1, max = 4, message = "You must choose correct answer")
    private int correctAnswer;

    public QuestionDto() {
        answers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }
}
