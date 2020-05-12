package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.TestResult;

import java.util.List;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
    TestResult findByStudentId(long id);
    List<TestResult> findAllByTestId(long id);
}
