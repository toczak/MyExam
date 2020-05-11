package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.potoczak.myexam.model.*;
import pl.potoczak.myexam.repository.QuestionRepository;
import pl.potoczak.myexam.repository.StudentRepository;
import pl.potoczak.myexam.repository.TestRepository;
import pl.potoczak.myexam.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private TestRepository testRepository;
    private StudentRepository studentRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public TestService(TestRepository testRepository, StudentRepository studentRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
    }

    public Iterable<Test> getAllTeacherTests() {
        return testRepository.findAllByTeacherId(getPrincipalTeacher().getId());
    }

    private Teacher getPrincipalTeacher() {
        return (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Iterable<Question> getAllTeacherQuestions() {
        return questionRepository.findAllByTeacherId(getPrincipalTeacher().getId());
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAllByRole_NameEquals("ROLE_STUDENT");
    }

    public void saveTest(Test test) {
        test.setTeacher(getPrincipalTeacher());
        testRepository.save(test);
    }

    public Test getTestById(long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.orElse(null);
    }

    public void deleteTest(Test test) {
        testRepository.delete(test);
    }
}
