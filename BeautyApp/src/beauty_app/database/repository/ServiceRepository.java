package beauty_app.database.repository;

import beauty_app.database.entities.ServicesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<ServicesEntity, Integer> {
    Iterable<ServicesEntity> findAllByType(String type);
    Iterable<ServicesEntity> findAllByIdIn(List<Integer> ids);
}
