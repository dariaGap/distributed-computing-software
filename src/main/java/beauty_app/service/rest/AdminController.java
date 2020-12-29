package beauty_app.service.rest;

import beauty_app.business_logic.*;
import beauty_app.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    EntryService entryService;
    @Autowired
    ComplaintService complaintService;

    @RequestMapping("/admin/transferEntry")
    public void transferEntry(@RequestParam(value="id") Integer id) {
        Entry entry = entryService.getEntry(id);
        entry.setState(Entry.EntryState.EXECUTING);
        entryService.updateEntry(entry);
    }

    @RequestMapping("/admin/transferComplaint")
    public void transferComplaint(@RequestParam(value="id") Integer id) {
        Complaint complaint = complaintService.getComplaint(id);
        complaint.setState(Complaint.ComplaintState.WAIT_EXPLANATORY);
        complaintService.updateComplaint(complaint);
    }
}
