package beauty_app.database.repository;

import beauty_app.database.entities.RolesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RolesEntity, Integer> {

}
