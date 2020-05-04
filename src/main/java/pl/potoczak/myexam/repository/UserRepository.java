package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.potoczak.myexam.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByUsername(String s);
}
