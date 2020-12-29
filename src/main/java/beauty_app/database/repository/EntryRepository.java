package beauty_app.database.repository;

import beauty_app.database.entities.EntriesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EntryRepository extends CrudRepository<EntriesEntity, Integer> {
    Iterable<EntriesEntity> findAllByMaster(Integer id);
    Iterable<EntriesEntity> findAllByEntryDate(Date date);
    Iterable<EntriesEntity> findAllByEntryDateAndState(Date date,String state);
    Iterable<EntriesEntity> findAllByMasterAndEntryDate(Integer master,Date date);
    Iterable<EntriesEntity> findAllByMasterAndEntryDateAndState(Integer master,
                                                                        Date date,
                                                                        String state);
}
