package pl.potoczak.myexam.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToMany(mappedBy = "students")
    private Collection<Teacher> teachers;

    @ManyToMany(mappedBy = "students")
    private Collection<Test> tests;

    @OneToMany(mappedBy = "student")
    private Collection<TestResult> testResults;

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Collection<Test> getTests() {
        return tests;
    }

    public void setTests(Collection<Test> tests) {
        this.tests = tests;
    }

    public Collection<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(Collection<TestResult> testResults) {
        this.testResults = testResults;
    }
}
