package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByTeacherId(long id);
}
