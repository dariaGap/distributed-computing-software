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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BeautyApp.class })
class TransferEntryTest {
    @Autowired
    ClientService clientService;
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService servicesService;
    @Autowired
    EntryService entryService;
    Entry entry;
    Service service;
    Master master;
    Client client;

    @BeforeEach
    public void setUp() {
        master = new Master("testMaster", Service.ServiceType.HAIRDRESSING,"testMaster");
        master = masterService.addMaster(master);

        service = new Service("testService", Service.ServiceType.HAIRDRESSING,45);
        service = servicesService.addService(service);
        List<Service> services = new ArrayList<>();
        services.add(service);

        client = new Client("testClient", "0(000)-000-00-00");
        client = clientService.addClient(client);

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse("2020-06-22");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date time = timeFormat.parse("15:30");
            entry = new Entry(date,time,master,services,client);
            addEntry(entry);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        clientService.removeClient(client);
        masterService.removeMaster(master);
        entryService.removeEntry(entry);
        servicesService.removeService(service);
    }

    @Test
    public void testTransferEntry() throws Exception {
        entry.setState(Entry.EntryState.EXECUTING);
        assertTrue(entryService.updateEntry(entry));
        Entry entry1 = entryService.getEntry(entry.getId());
        assertTrue(entry1.getState().equals(Entry.EntryState.EXECUTING));
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