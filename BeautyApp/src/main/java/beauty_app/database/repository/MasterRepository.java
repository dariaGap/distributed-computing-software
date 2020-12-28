package beauty_app.database.repository;

import beauty_app.database.entities.MastersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRepository extends CrudRepository<MastersEntity, Integer> {
    Optional<MastersEntity> findByUsername(String login);
    Iterable<MastersEntity> findAllByServices(String type);
}
