package beauty_app.database.repository;

import beauty_app.database.entities.AdminsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AdminsEntity, Integer> {

    Optional<AdminsEntity> findByLogin(String login);
}
