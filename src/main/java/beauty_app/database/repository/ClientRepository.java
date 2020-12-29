package beauty_app.database.repository;

import beauty_app.database.entities.ClientsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<ClientsEntity, Integer> {
    Optional<ClientsEntity> findByPhone(String phone);
}
