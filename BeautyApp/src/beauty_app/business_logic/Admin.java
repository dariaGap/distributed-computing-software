package beauty_app.business_logic;

import beauty_app.database.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Admin extends Employee {
    @Autowired
    EntryService entryService;

    public Admin(){ }

    public Admin(String name, String password){
        super(name,password, EmployeeType.ADMIN);
    }

    public Admin(Integer id, String name, String password){
        super(id, name,password, EmployeeType.ADMIN);
    }

    public Boolean transferEntry(Entry entry){
        entry.setState(Entry.EntryState.EXECUTING);
        return entryService.updateEntry(entry);
    }

    public Boolean transferComplaint(Complaint complaint){
        complaint.setState(Complaint.ComplaintState.WAIT_EXPLANATORY);
        //return repo.updateComplaint(complaint);
        return true;
    }
}
