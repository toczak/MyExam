package pl.potoczak.myexam.repository;

import org.springframework.stereotype.Repository;
import pl.potoczak.myexam.model.Student;
import pl.potoczak.myexam.model.User;

@Repository
public interface StudentRepository extends UserBaseRepository<Student> {
}
