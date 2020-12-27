package beauty_app.business_logic;

import beauty_app.database.EntryService;
import beauty_app.database.MasterService;
import beauty_app.database.ServicesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Master extends Employee {

    private String name;
    private Service.ServiceType services;
    @Autowired
    EntryService entryService;
    @Autowired
    ServicesService servicesService;

    public Master(){}

    public Master(String name, String login, String password, Service.ServiceType services){
        super(login,password, EmployeeType.MASTER);
        this.name = name;
        this.services = services;
    }

    public Master(Integer id, String name, String login, String password, Service.ServiceType services){
        super(id, login, password, EmployeeType.MASTER);
        this.name = name;
        this.services = services;
    }

    public String getName(){
        return name;
    }

    public Service.ServiceType getServices(){
        return services;
    }

    @Override
    public String toString() {
        return this.name + " (" + Service.ServiceType.serviceTypeToString(this.services) + ")";
    }

    public List<Service> getPossibleServices(){
        return servicesService.getServices(services);
    }

    public Boolean addExplanatory(Complaint complaint, String explanatory){
        /*if(complaint.getState().equals(Complaint.ComplaintState.WAIT_EXPLANATORY)) {
            complaint.setExplanatory(explanatory);
            complaint.setState(Complaint.ComplaintState.WAIT_DECISION);
            return repo.updateComplaint(complaint);
        }*/
        return false;
    }
}
