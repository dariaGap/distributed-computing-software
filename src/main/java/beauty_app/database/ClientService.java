package beauty_app.database;

import beauty_app.business_logic.Client;
import beauty_app.database.entities.ClientsEntity;
import beauty_app.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private static Map<Integer, Client> loadedClients = new HashMap<>();

    public static Client convertEntityToClient(Optional<ClientsEntity> entity) {
        if (entity.isPresent()) {
            ClientsEntity entity1 = entity.get();
            Client client = new Client(entity1.getId(),entity1.getName(),entity1.getPhone());
            loadedClients.put(client.getId(),client);
            return client;
        }
        return null;
    }

    public static ClientsEntity convertClientToEntity(Client client) {
        ClientsEntity entity = new ClientsEntity();
        if (client.getId() != null) {
            entity.setId(client.getId());
        }
        entity.setName(client.getName());
        entity.setPhone(client.getPhone());
        return entity;
    }

    @Transactional
    public Client addClient(Client client) {
        ClientsEntity entity = convertClientToEntity(client);
        entity = clientRepository.save(entity);
        client.setId(entity.getId());
        loadedClients.put(client.getId(),client);
        return client;
    }

    @Transactional
    public Client getClient(Integer id) {
        Client client = loadedClients.get(id);
        if(client != null){
            return client;
        }
        Optional<ClientsEntity> entity = clientRepository.findById(id);
        return convertEntityToClient(entity);
    }

    @Transactional
    public Client getClient(String phone){
        for(Client client: loadedClients.values()){
            if(client.getPhone().equals(phone)) {
                return client;
            }
        }
        Optional<ClientsEntity> entity = clientRepository.findByPhone(phone);
        return convertEntityToClient(entity);
    }

    @Transactional
    public Boolean removeClient(Client client){
        Integer id = client.getId();
        clientRepository.deleteById(id);
        loadedClients.remove(id);
        return !clientRepository.existsById(id);
    }
}
