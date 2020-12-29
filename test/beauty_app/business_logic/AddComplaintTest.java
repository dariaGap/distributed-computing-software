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

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BeautyApp.class })
public class AddComplaintTest {
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
    Entry entry;
    Service service;
    Master master;
    Client client;
    Complaint complaint;

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
            entryService.addEntry(entry);

            complaint = new Complaint(entry,"Test text");
            complaint.setState(Complaint.ComplaintState.WAIT_EXPLANATORY);
            complaint = complaintService.addComplaint(complaint);

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
        complaintService.removeComplaint(complaint);
    }

    @Test
    public void testAddComplaint() throws Exception {
        complaint.setState(Complaint.ComplaintState.WAIT_DECISION);
        complaint.setExplanatory("test explanatory");
        complaintService.updateComplaint(complaint);
        Complaint complaint1 = complaintService.getComplaint(complaint.getId());
        assertTrue(complaint1.getState().equals(Complaint.ComplaintState.WAIT_DECISION));
    }
}
