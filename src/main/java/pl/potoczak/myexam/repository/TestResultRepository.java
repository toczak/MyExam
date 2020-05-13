package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.TestResult;

import java.util.List;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
    TestResult findByIdAndStudentId(long id, long student_id);
    TestResult findByTestIdAndStudentId(long id, long student_id);
    List<TestResult> findAllByTestId(long id);
    Iterable<TestResult> findAllByStudentId(Long id);

}
