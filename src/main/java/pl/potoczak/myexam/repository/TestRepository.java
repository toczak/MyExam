package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.Test;

public interface TestRepository extends CrudRepository<Test, Long> {
    Iterable<Test> findAllByTeacherId(Long id);
}
