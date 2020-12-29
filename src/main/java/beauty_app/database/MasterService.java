package beauty_app.database;

import beauty_app.business_logic.Master;
import beauty_app.database.entities.MastersEntity;
import beauty_app.database.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MasterService {
    @Autowired
    private MasterRepository masterRepository;

    private static Map<Integer, Master> loadedMasters = new HashMap<>();

    public static Master convertEntityToMaster(Optional<MastersEntity> entity) {
        if (entity.isPresent()) {
            MastersEntity entity1 = entity.get();
            Master master = new Master(entity1.getId(), entity1.getName(),
                    beauty_app.business_logic.Service.ServiceType
                            .stringToServiceType(entity1.getServices()), entity1.getUsername());
            loadedMasters.put(master.getId(),master);
            return master;
        }
        return null;
    }

    public static MastersEntity convertMasterToEntity(Master master) {
        MastersEntity entity = new MastersEntity();
        if (master.getId() != null) {
            entity.setId(master.getId());
        }
        entity.setName(master.getName());
        entity.setServices(beauty_app.business_logic.Service.ServiceType.serviceTypeToString(master.getServices()));
        entity.setUsername(master.getLogin());
        return entity;
    }

    @Transactional
    public Master addMaster(Master master) {
        MastersEntity entity = convertMasterToEntity(master);
        entity = masterRepository.save(entity);
        master.setId(entity.getId());
        loadedMasters.put(master.getId(),master);
        return master;
    }

    @Transactional
    public List<Master> getMasters(){
        List<Master> masters = new ArrayList<>();
        Iterable<MastersEntity> entities = masterRepository.findAll();
        entities.forEach(entity -> masters.add(convertEntityToMaster(Optional.of(entity))));
        return masters;
    }

    @Transactional
    public Master getMaster(Integer id){
        Master master = loadedMasters.get(id);
        if(master != null){
            return master;
        }
        Optional<MastersEntity> entity = masterRepository.findById(id);
        return convertEntityToMaster(entity);
    }

    @Transactional
    public Master getMaster(String login){
        for(Master master: loadedMasters.values()){
            if(master.getLogin().equals(login))
                return master;
        }
        Optional<MastersEntity> entity = masterRepository.findByUsername(login);
        return convertEntityToMaster(entity);
    }


    public List<Master> getMasters(beauty_app.business_logic.Service.ServiceType type){
        List<Master> masters = new ArrayList<>();
        Iterable<MastersEntity> entities = masterRepository.findAllByServices(
                beauty_app.business_logic.Service.ServiceType.serviceTypeToString(type));
        entities.forEach(entity -> masters.add(convertEntityToMaster(Optional.of(entity))));
        return masters;
    }

    @Transactional
    public Boolean removeMaster(Master master){
        Integer id = master.getId();
        masterRepository.deleteById(id);
        loadedMasters.remove(id);
        return !masterRepository.existsById(id);
    }
}
