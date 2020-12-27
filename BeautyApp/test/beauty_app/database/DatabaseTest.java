package beauty_app.database;

import beauty_app.main.BeautyApp;
import beauty_app.business_logic.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BeautyApp.class })
class DatabaseTest {

    @Autowired
    AdminService adminService;
    @Autowired
    ClientService clientService;
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService serviceService;
    @Autowired
    EntryService entryService;

    @org.junit.jupiter.api.Test
    void adminTest() {
        Admin admin1 = new Admin("admin1","admin1");
        Admin admin2 = adminService.addAdmin(admin1);
        assertEquals(admin2.getLogin(),admin1.getLogin());

        Admin admin3 = adminService.getAdmin(admin2.getLogin());
        assertEquals(admin3.getId(),admin2.getId());

        assertTrue(adminService.removeAdmin(admin3));

        assertEquals(null,adminService.getAdmin(admin3.getLogin()));
    }


    @org.junit.jupiter.api.Test
    void clientTest() {
        Client client1 = new Client("client1","123-45-67");
        Client client2 = clientService.addClient(client1);
        assertEquals(client2.getName(),client1.getName());

        Client client3 = clientService.getClient(client2.getPhone());
        assertEquals(client3.getId(),client3.getId());

        assertTrue(clientService.removeClient(client3));

        assertEquals(null,clientService.getClient(client3.getId()));
    }

  /*  @org.junit.jupiter.api.Test
    void masterTest() {
        Master master1;
        Master master2;
        Service service1;
        Service service2;
        Service service3;
        service1 = new Service("Стрижка. Длинные волосы", Service.ServiceType.HAIRDRESSING,60);
        service1 = serviceService.addService(service1);
        service2 = new Service("Стрижка. Короткие волосы", Service.ServiceType.HAIRDRESSING,30);
        service2 = serviceService.addService(service2);

        service3 = new Service("Маникюр", Service.ServiceType.MANICURE,40);
        service3 = serviceService.addService(service3);

        service3 = new Service("Покрытие", Service.ServiceType.MANICURE,30);
        service3 = serviceService.addService(service3);


        master1 = new Master("Мария К.","mariaK","mariaK",
                Service.ServiceType.HAIRDRESSING);
        master1 = masterService.addMaster(master1);

        master2 = new Master("Алена С.","alenaS","alenaS",
                Service.ServiceType.HAIRDRESSING);
        master2 = masterService.addMaster(master2);

        master2 = new Master("Вероника М.","veronikaM","veronikaM",
                Service.ServiceType.MANICURE);
        master2 = masterService.addMaster(master2);
    }*/
}