package beauty_app.database;

import beauty_app.business_logic.Admin;
import beauty_app.database.entities.AdminsEntity;
import beauty_app.database.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    private static Map<Integer, Admin> loadedAdmins = new HashMap<>();

    private Admin convertEntityToAdmin(Optional<AdminsEntity> entity) {
        if (entity.isPresent()) {
            AdminsEntity entity1 = entity.get();
            Admin admin = new Admin(entity1.getId(),entity1.getLogin(),entity1.getPassword());
            loadedAdmins.put(admin.getId(),admin);
            return admin;
        }
        return null;
    }

    private AdminsEntity convertAdminToEntity(Admin admin) {
        AdminsEntity entity = new AdminsEntity();
        if (admin.getId() != null) {
            entity.setId(admin.getId());
        }
        entity.setLogin(admin.getLogin());
        entity.setPassword(admin.getPassword());
        return entity;
    }

    @Transactional
    public Admin addAdmin(Admin admin) {
        AdminsEntity entity = convertAdminToEntity(admin);
        entity = adminRepository.save(entity);
        admin.setId(entity.getId());
        loadedAdmins.put(admin.getId(),admin);
        return admin;
    }

    @Transactional
    public Admin getAdmin(String login){
        for(Admin admin: loadedAdmins.values()){
            if(admin.getLogin().equals(login))
                return admin;
        }
        Optional<AdminsEntity> entity = adminRepository.findByLogin(login);
        return convertEntityToAdmin(entity);
    }

    @Transactional
    public Boolean removeAdmin(Admin admin){
        Integer id = admin.getId();
        adminRepository.deleteById(id);
        loadedAdmins.remove(id);
        return !adminRepository.existsById(id);
    }
}
