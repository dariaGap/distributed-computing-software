package beauty_app.business_logic;

import beauty_app.BeautyApp;
import beauty_app.database.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BeautyApp.class })
class NewEntryTest {
    Entry newEntry;
    Service service;
    Master master;
    Client client;
    @Autowired
    ClientService clientService;
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService servicesService;
    @Autowired
    ComplaintService complaintService;
    @Autowired
    EntryService entryService;

    @BeforeEach
    public void setUp() {
        master = new Master("testMaster", Service.ServiceType.HAIRDRESSING,"testMaster");
        master = masterService.addMaster(master);

        service = new Service("testService", Service.ServiceType.HAIRDRESSING,45);
        service = servicesService.addService(service);
    }

    @AfterEach
    public void tearDown() {
        client = clientService.getClient(client.getPhone());
        clientService.removeClient(client);
        masterService.removeMaster(master);
        entryService.removeEntry(newEntry);
        servicesService.removeService(service);
    }

    @Test
    public void testNewEntry() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2020-06-21");

        DateFormat format = new SimpleDateFormat("HH:mm");
        List<String> possibleTime = new ArrayList<>();
        for(int i = 10; i<= 18; i++){
            possibleTime.add(i + ":00");
            possibleTime.add(i + ":30");
        }

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date time = timeFormat.parse(possibleTime.get(0));

        List<Service> possibleServices = servicesService.getServices(master.getServices());

        assertTrue(possibleServices.contains(service));

        client = new Client("testClient", "0(000)-000-00-00");

        newEntry = new Entry(date, time,master,possibleServices, client);;

        assertTrue(addEntry(newEntry));
    }

    private Boolean addEntry(Entry entry){
        if(entry.getClient().getId() == null) {
            Client cl = clientService.getClient(entry.getClient().getPhone());
            if (cl == null)
                cl = clientService.addClient(entry.getClient());
            if (cl == null)
                return false;
            entry.setClient(cl);
        }
        Entry savedEntry = entryService.addEntry(entry);
        if (savedEntry.getId() == 0)
            return false;
        entry.setId(savedEntry.getId());
        return true;
    }
}