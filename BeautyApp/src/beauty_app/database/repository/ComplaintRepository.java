package beauty_app.database.repository;

import beauty_app.database.entities.ComplaintsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends CrudRepository<ComplaintsEntity, Integer> {
    Iterable<ComplaintsEntity> findAllByState(String state);
}
