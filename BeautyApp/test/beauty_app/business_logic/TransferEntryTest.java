package beauty_app.business_logic;

import beauty_app.database.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TransferEntryTest {
    @Autowired
    ClientService clientService;
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService servicesService;
    @Autowired
    AdminService adminService;
    @Autowired
    EntryService entryService;
    Entry entry;
    Service service;
    Master master;
    Client client;
    Admin admin;

    @BeforeEach
    public void setUp() {
        //repo = Repository.getInstance();

        admin = new Admin("testAdmin", "testAdmin");
        admin = adminService.addAdmin(admin);

        master = new Master("testMaster","testMaster","testMaster", Service.ServiceType.HAIRDRESSING);
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
            //entry.addEntry();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        adminService.removeAdmin(admin);
        clientService.removeClient(client);
        masterService.removeMaster(master);
        servicesService.removeService(service);
        entryService.removeEntry(entry);
    }

    @Test
    public void testTransferEntry() throws Exception {
        assertTrue(admin.authorize("testAdmin"));

        admin.transferEntry(entry);
        assertTrue(entry.getState().equals(Entry.EntryState.EXECUTING));

        entry = entryService.getEntry(entry.getId());
        assertTrue(entry.getState().equals(Entry.EntryState.EXECUTING));
    }
}