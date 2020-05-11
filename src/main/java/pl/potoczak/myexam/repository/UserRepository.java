package pl.potoczak.myexam.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.potoczak.myexam.model.User;

@Repository
public interface UserRepository extends UserBaseRepository<User> {
}
