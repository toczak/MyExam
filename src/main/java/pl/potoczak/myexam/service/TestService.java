package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.potoczak.myexam.model.*;
import pl.potoczak.myexam.repository.QuestionRepository;
import pl.potoczak.myexam.repository.StudentRepository;
import pl.potoczak.myexam.repository.TestRepository;
import pl.potoczak.myexam.repository.TestResultRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private TestRepository testRepository;
    private StudentRepository studentRepository;
    private QuestionRepository questionRepository;
    private TestResultRepository testResultRepository;

    @Autowired
    public TestService(TestRepository testRepository, StudentRepository studentRepository, QuestionRepository questionRepository, TestResultRepository testResultRepository) {
        this.testRepository = testRepository;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
        this.testResultRepository = testResultRepository;
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

    private void saveTest(Test test){
        test.setTeacher(getPrincipalTeacher());
        testRepository.save(test);
    }

    public void addTest(Test test) {
        saveTest(test);
        addAndSaveTestsResult(test);
    }

    public void editTest(Test test) {
        saveTest(test);
        deleteOrSkipUsersTestResultFromTest(test);
        addAndSaveTestsResult(test);
    }

    private void addAndSaveTestsResult(Test test){
        for (Student student : test.getStudents()) {
            TestResult testResult = new TestResult();
            testResult.setStudent(student);
            testResult.setTest(test);
            testResult.setEnabled(true);
            testResultRepository.save(testResult);
        }
    }

    private void deleteOrSkipUsersTestResultFromTest(Test test) {
        List<TestResult> testResults = testResultRepository.findAllByTestId(test.getId());
        for (TestResult testResult : testResults) {
            if (test.getStudents().contains(testResult.getStudent())) {
                test.getStudents().remove(testResult.getStudent());
            } else {
                testResultRepository.delete(testResult);
            }
        }
    }

    public Test getTestById(long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.orElse(null);
    }

    public void deleteTest(Test test) {
        testRepository.delete(test);
    }
}
