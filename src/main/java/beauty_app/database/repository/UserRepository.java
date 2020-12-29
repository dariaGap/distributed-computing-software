package beauty_app.database.repository;

import beauty_app.database.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UsersEntity, Integer> {
    UsersEntity findByUsername(String username);
}
