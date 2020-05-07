package pl.potoczak.myexam.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.potoczak.myexam.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByUsername(String s);
    User findUserByEmail(String s);
    Iterable<User> findAll();
//    boolean existsByUsernameAndIdNotIn(String username, Long id);
//
//    boolean existsByUsername(String username);
//    boolean existsUserByUsernameExistsOrEmailExists(String username, String email);
//    boolean existsUserByUsernameExistsAndIdIsNotIn(String username, Long id);
}
