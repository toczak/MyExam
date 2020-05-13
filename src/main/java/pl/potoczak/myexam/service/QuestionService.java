package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.potoczak.myexam.dto.QuestionDto;
import pl.potoczak.myexam.model.Question;
import pl.potoczak.myexam.model.Teacher;
import pl.potoczak.myexam.repository.QuestionRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Iterable<Question> getAllTeacherQuestions() {
        return questionRepository.findAllByTeacherId(getPrincipalTeacher().getId());
    }

    private Teacher getPrincipalTeacher() {
        return (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Question getNewQuestionFromDto(QuestionDto questionDto) {
        Question question = new Question();
        question.setText(questionDto.getText());
        question.setAnswers(questionDto.getAnswers());
        question.setCorrectAnswer(questionDto.getAnswers().get(questionDto.getCorrectAnswer() - 1));
        question.getAnswers().forEach(a -> a.setQuestion(question));
        question.setTeacher(getPrincipalTeacher());
        return question;
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question getQuestionById(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question question = optionalQuestion.orElse(null);
        if (question != null && !question.getTeacher().equals(getPrincipalTeacher())) question = null;
        return question;
    }

    public QuestionDto getQuestionDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        questionDto.setAnswers(question.getAnswers());
        questionDto.setCorrectAnswer(getIndexOfCorrectAnswer(question));
        return questionDto;
    }

    private int getIndexOfCorrectAnswer(Question question) {
        return question.getAnswers().indexOf(question.getCorrectAnswer()) + 1;
    }

    public Question getEditedQuestionFromDTO(QuestionDto questionDto) {
        Optional<Question> questionOptional = questionRepository.findById(questionDto.getId());
        Question question = questionOptional.orElse(null);
        if (question != null) {
            question.setId(questionDto.getId());
            question.setText(questionDto.getText());
            IntStream.range(0, question.getAnswers().size())
                    .forEach(i -> question.getAnswers().get(i).setText(questionDto.getAnswers().get(i).getText()));
            question.setCorrectAnswer(question.getAnswers().get(questionDto.getCorrectAnswer() - 1));
            question.setTeacher(getPrincipalTeacher());
        }
        return question;
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
