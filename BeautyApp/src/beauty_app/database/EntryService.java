package beauty_app.database;

import beauty_app.business_logic.Client;
import beauty_app.business_logic.Entry;
import beauty_app.business_logic.Master;
import beauty_app.database.entities.EntriesEntity;
import beauty_app.database.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private MasterService masterService;

    private static Map<Integer, Entry> loadedEntries = new HashMap<>();

    private Entry convertEntityToEntry(Optional<EntriesEntity> entity1) {
        if (entity1.isPresent()) {
            EntriesEntity entity = entity1.get();

            List<beauty_app.business_logic.Service> services =
                    ServicesService.convertEntitiesToServices(entity.getServices());
            Client client = clientService.getClient(entity.getClient());
            Master master = masterService.getMaster(entity.getMaster());
            Entry entry = new Entry(entity.getId(), entity.getEntryDate(),
                    entity.getEntryTime(), master, services, client,
                    Entry.EntryState.stringToEntryState(entity.getState()));
            loadedEntries.put(entry.getId(), entry);
            return entry;
        }
        return null;
    }

    private EntriesEntity convertEntryToEntity(Entry entry) {
        EntriesEntity entity = new EntriesEntity();
        if (entry.getId() != null) {
            entity.setId(entry.getId());
        }

        entity.setEntryDate(entry.getDate());
        entity.setEntryTime(entry.getTime());
        entity.setMaster(entry.getMaster().getId());
        entity.setState(Entry.EntryState.entryStateToString(entry.getState()));
        entity.setServices(ServicesService.convertServicesToEntities(entry.getServices()));
        entity.setClient(entry.getClient().getId());
        return entity;
    }

    private List<Entry> convertEntitiesToEntries(Iterable<EntriesEntity> entities) {
        List<Entry> entries = new ArrayList<>();
        entities.forEach(entity -> entries.add(convertEntityToEntry(Optional.of(entity))));
        return entries;
    }

    @Transactional
    public Entry addEntry(Entry entry) {
        EntriesEntity entity = convertEntryToEntity(entry);
        entity = entryRepository.save(entity);
        entry.setId(entity.getId());
        loadedEntries.put(entry.getId(),entry);
        return entry;
    }

    @Transactional
    public Boolean updateEntry(Entry entry) {
        Optional<EntriesEntity> entity = entryRepository.findById(entry.getId());
        if (entity.isPresent()) {
            addEntry(entry);
            return true;
        }
        return false;
    }

    @Transactional
    public Entry getEntry(Integer id) {
        Entry entry = loadedEntries.get(id);
        if (entry != null) {
            return entry;
        }
        Optional<EntriesEntity> entities = entryRepository.findById(id);
        return convertEntityToEntry(entities);
    }

    @Transactional
    public List<Entry> getEntries(){
        return convertEntitiesToEntries(entryRepository.findAll());
    }

    @Transactional
    public List<Entry> getEntries(Master master){
        return convertEntitiesToEntries(entryRepository.findAllByMaster(master.getId()));
    }

    @Transactional
    public List<Entry> getEntries(Date date){
        return convertEntitiesToEntries(entryRepository.findAllByEntryDate(date));
    }

    @Transactional
    public List<Entry> getEntries(Date date, Master master){
        return convertEntitiesToEntries(entryRepository.findAllByMasterAndEntryDate(
                master.getId(), date));
    }

    @Transactional
    public List<Entry> getEntries(Date date, Entry.EntryState state, Master master) {
        return convertEntitiesToEntries(
                entryRepository.findAllByMasterAndEntryDateAndState(master.getId(),
                        date,
                        Entry.EntryState.entryStateToString(state)));
    }

    @Transactional
    public Boolean removeEntry(Entry entry){
        Integer id = entry.getId();
        entryRepository.deleteById(id);
        loadedEntries.remove(id);
        return !entryRepository.existsById(id);
    }
}
