package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.Answer;
import pl.potoczak.myexam.model.Question;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Answer findById(long id);
}
