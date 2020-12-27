package beauty_app.business_logic;

import beauty_app.database.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        //repo = Repository.getInstance();

        master = new Master("testMaster","testMaster","testMaster", Service.ServiceType.HAIRDRESSING);
        master = masterService.addMaster(master);

        service = new Service("testService", Service.ServiceType.HAIRDRESSING,45);
        service = servicesService.addService(service);
    }

    @AfterEach
    public void tearDown() {
        client = clientService.getClient(client.getPhone());
        clientService.removeClient(client);
        masterService.removeMaster(master);
        servicesService.removeService(service);
        entryService.removeEntry(newEntry);
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
       // possibleTime = master.getFreeTime(possibleTime,format,"2020-06-21");

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date time = timeFormat.parse(possibleTime.get(0));

        List<Service> possibleServices = master.getPossibleServices();
        assertTrue(possibleServices.get(0).getType().equals(master.getServices()));
        assertTrue(possibleServices.contains(service));

        client = new Client("testClient", "0(000)-000-00-00");

        newEntry = new Entry(date, time,master,possibleServices, client);;

        //assertTrue(newEntry.addEntry());
    }
}