package beauty_app.database;

import beauty_app.main.BeautyApp;
import beauty_app.business_logic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BeautyApp.class })
class EntryTest {
    @Autowired
    ClientService clientService;
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService serviceService;
    @Autowired
    EntryService entryService;

    Entry entry1;
    Entry entry2;
    Service service;
    Master master1;
    Master master2;
    Client client1;
    Service service1;
    Service service2;

    @BeforeEach
    public void setUp() {
        client1 = new Client("client1","123-45-67");
        client1 = clientService.addClient(client1);

        service1 = new Service("hair1", Service.ServiceType.HAIRDRESSING,30);
        service1 = serviceService.addService(service1);
        service2 = new Service("hair2", Service.ServiceType.HAIRDRESSING,30);
        service2 = serviceService.addService(service2);

        master1 = new Master("master1","master1","master1",
                Service.ServiceType.HAIRDRESSING);
        master1 = masterService.addMaster(master1);

        master2 = new Master("master2","master2","master2",
                Service.ServiceType.HAIRDRESSING);
        master2 = masterService.addMaster(master2);

        Service service = new Service("testService", Service.ServiceType.HAIRDRESSING,45);
        service = serviceService.addService(service);
    }

    @AfterEach
    public void tearDown() {
        clientService.removeClient(client1);
        masterService.removeMaster(master1);
        masterService.removeMaster(master2);
        entryService.removeEntry(entry1);
        entryService.removeEntry(entry2);
        serviceService.removeService(service1);
        serviceService.removeService(service2);
    }

    @org.junit.jupiter.api.Test
    void entryTest() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2018-01-01");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        List<String> possibleTime = new ArrayList<>();
        for(int i = 10; i<= 18; i++){
            possibleTime.add(i + ":00");
            possibleTime.add(i + ":30");
        }

        Date time = timeFormat.parse(possibleTime.get(0));

        List<Service> possibleServices = serviceService.getServices(master1.getServices());
        assertTrue(possibleServices.get(0).getType().equals(master1.getServices()));

        entry1 = new Entry(date,time, master1,possibleServices, client1);

        entry1 = entryService.addEntry(entry1);

        time = timeFormat.parse(possibleTime.get(1));

        entry2 = new Entry(date,time, master2,possibleServices, client1);
        entry2 = entryService.addEntry(entry2);

        List<Entry> entries = entryService.getEntries(date);

        assertTrue(entries.size()>=2);

        entries = entryService.getEntries(date,master2);

        Boolean flag = false;
        for (Entry entr : entries) {
            if(entr.equals(entry2)) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }
}