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

import static org.junit.jupiter.api.Assertions.assertTrue;

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
       // repo = Repository.getInstance();

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
        servicesService.removeService(service);
        entryService.removeEntry(entry);
        complaintService.removeComplaint(complaint);
    }

    @Test
    public void testAddComplaint() throws Exception {
        assertTrue(master.authorize("testMaster"));
        master.addExplanatory(complaint, "test explanatory");
        assertTrue(complaint.getState().equals(Complaint.ComplaintState.WAIT_DECISION));

        complaint = complaintService.getComplaint(complaint.getId());
        assertTrue(complaint.getState().equals(Complaint.ComplaintState.WAIT_DECISION));
    }
}
