package pl.potoczak.myexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.potoczak.myexam.model.*;
import pl.potoczak.myexam.repository.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private TestRepository testRepository;
    private StudentRepository studentRepository;
    private QuestionRepository questionRepository;
    private TestResultRepository testResultRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public TestService(TestRepository testRepository, StudentRepository studentRepository, QuestionRepository questionRepository, TestResultRepository testResultRepository, AnswerRepository answerRepository) {
        this.testRepository = testRepository;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
        this.testResultRepository = testResultRepository;
        this.answerRepository = answerRepository;
    }

    public Iterable<Test> getAllLoggedInTeacherTests() {
        return testRepository.findAllByTeacherId(getPrincipalTeacher().getId());
    }

    private Teacher getPrincipalTeacher() {
        return (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Student getPrincipalStudent() {
        return (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Iterable<Question> getAllLoggedInTeacherQuestions() {
        return questionRepository.findAllByTeacherId(getPrincipalTeacher().getId());
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAllByRole_NameEquals("ROLE_STUDENT");
    }

    public Iterable<TestResult> getAllLoggedInStudentTestResults() {
        return testResultRepository.findAllByStudentId(getPrincipalStudent().getId());
    }

    public TestResult getTestResultByLoggedInUser(long testResultId) {
        return testResultRepository.findByIdAndStudentId(testResultId, getPrincipalStudent().getId());
    }

    private void saveTestLoggedInTeacher(Test test) {
        test.setTeacher(getPrincipalTeacher());
        testRepository.save(test);
    }

    public void addTest(Test test) {
        saveTestLoggedInTeacher(test);
        addAndSaveTestsResult(test);
    }

    public void editTest(Test test) {
        saveTestLoggedInTeacher(test);
        deleteOrSkipUsersTestResultFromTest(test);
        addAndSaveTestsResult(test);
    }

    private void addAndSaveTestsResult(Test test) {
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

    public Test getLoggedInTeacherTestById(long id) {
        Optional<Test> optionalTest = testRepository.findById(id);
        Test test = optionalTest.orElse(null);
        if (test != null && !test.getTeacher().equals(getPrincipalTeacher())) test = null;
        return test;
    }

    public void deleteTest(Test test) {
        testRepository.delete(test);
    }

    public void fillQuestionsInTestResultAndCheckTest(long id, Collection<Answer> answerList) {
        TestResult testResult = getTestResultByLoggedInUser(id);
        for (Answer checkedAnswer : answerList) {
            Optional<Answer> answerOptional = answerRepository.findById(checkedAnswer.getId());
            Answer answer = answerOptional.orElse(null);
            testResult.getAnswerList().add(answer);
        }
        testResult.setEnabled(false);
        checkAndSetNumberOfCorrectQuestions(testResult);
        testResultRepository.save(testResult);
    }

    public void shuffleQuestionsAndAnswers(TestResult testResult) {
        Collection<Question> questions = testResult.getTest().getQuestions();
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers();
            Collections.shuffle(answers);
            question.setAnswers(answers);
        }
        Collections.shuffle((List<?>) questions);
        testResult.getTest().setQuestions(questions);
    }

    private void checkAndSetNumberOfCorrectQuestions(TestResult testResult) {
        int value = 0;
        for (Answer answer : testResult.getAnswerList()) {
            if (answer.equals(answer.getQuestion().getCorrectAnswer())) value++;
        }
        testResult.setCorrectQuestions(value);
    }

    public Collection<TestResult> getAllTestResultsByTest(long id) {
        return testResultRepository.findAllByTestId(id);
    }

    public TestResult getTestResultById(long idTestResult) {
        Optional<TestResult> optionalTestResult = testResultRepository.findById(idTestResult);
        return optionalTestResult.orElse(null);
    }
}
