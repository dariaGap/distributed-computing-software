package beauty_app.database;

import beauty_app.database.entities.ServicesEntity;
import beauty_app.database.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;

    private static Map<Integer, beauty_app.business_logic.Service> loadedServices = new HashMap<>();

    public static beauty_app.business_logic.Service convertEntityToService(ServicesEntity entity) {
        beauty_app.business_logic.Service service = new beauty_app.business_logic.Service(
                entity.getId(),
                entity.getName(),
                beauty_app.business_logic.Service.ServiceType.stringToServiceType(entity.getType()),
                entity.getLength());
        loadedServices.put(service.getId(),service);
        return service;
    }

    public static List<beauty_app.business_logic.Service> convertEntitiesToServices(
            List<ServicesEntity> entities) {
        List<beauty_app.business_logic.Service> services = new ArrayList<>();
        for (ServicesEntity entity : entities) {
            services.add(convertEntityToService(entity));
        }
        return services;
    }

    public static ServicesEntity convertServiceToEntity(beauty_app.business_logic.Service service) {
        ServicesEntity entity = new ServicesEntity();
        if (service.getId() != null) {
            entity.setId(service.getId());
        }
        entity.setName(service.getName());
        entity.setType(beauty_app.business_logic.Service.ServiceType.serviceTypeToString(service.getType()));
        entity.setLength(service.getLength());
        return entity;
    }

    public static List<ServicesEntity> convertServicesToEntities(
            List<beauty_app.business_logic.Service> services) {
        List<ServicesEntity> entities = new ArrayList<>();
        for (beauty_app.business_logic.Service service : services) {
            entities.add(convertServiceToEntity(service));
        }
        return entities;
    }

    @Transactional
    public beauty_app.business_logic.Service addService(beauty_app.business_logic.Service service) {
        ServicesEntity entity = convertServiceToEntity(service);
        entity = serviceRepository.save(entity);
        service.setId(entity.getId());
        return service;
    }

    @Transactional
    public List<beauty_app.business_logic.Service> getServices() {
        List<beauty_app.business_logic.Service> services = new ArrayList<>();
        Iterable<ServicesEntity> entities = serviceRepository.findAll();
        entities.forEach(entity -> services.add(convertEntityToService(entity)));
        return services;
    }

    @Transactional
    public beauty_app.business_logic.Service getService(Integer id) {
        beauty_app.business_logic.Service service = loadedServices.get(id);
        if (service == null) {
            Optional<ServicesEntity> entity = serviceRepository.findById(id);
            return entity.isPresent() ? convertEntityToService(entity.get()) : null;
        }
        return service;
    }

    @Transactional
    public List<beauty_app.business_logic.Service> getServices(beauty_app.business_logic.Service.ServiceType type){
        List<beauty_app.business_logic.Service> services = new ArrayList<>();
        Iterable<ServicesEntity> entities = serviceRepository.findAllByType(
                beauty_app.business_logic.Service.ServiceType.serviceTypeToString(type));
        entities.forEach(entity -> services.add(convertEntityToService(entity)));
        return services;
    }

    @Transactional
    public List<beauty_app.business_logic.Service> getServices(List<Integer> ids){
        List<beauty_app.business_logic.Service> services = new ArrayList<>();
        Iterable<ServicesEntity> entities = serviceRepository.findAllByIdIn(ids);
        entities.forEach(entity -> services.add(convertEntityToService(entity)));
        return services;
    }

    @Transactional
    public Boolean removeService(beauty_app.business_logic.Service service){
        Integer id = service.getId();
        serviceRepository.deleteById(id);
        loadedServices.remove(id);
        return !serviceRepository.existsById(id);
    }
}
