package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import pl.potoczak.myexam.model.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Long> {
    T findUserByUsername(String s);

    T findUserByUsernameAndIdNotLike(String s, long id);

    T findUserByEmail(String s);

    T findUserByEmailAndIdNotLike(String s, long id);

    T findUserById(long id);

    Iterable<T> findAll();

    Iterable<T> findAllByRole_NameEquals(String roleName);
}
