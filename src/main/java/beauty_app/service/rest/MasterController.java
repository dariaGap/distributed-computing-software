package beauty_app.service.rest;

import beauty_app.business_logic.Complaint;
import beauty_app.database.ComplaintService;
import beauty_app.database.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterController {
    @Autowired
    EntryService entryService;
    @Autowired
    ComplaintService complaintService;

    @RequestMapping("/master/addExplanatory")
    public void transferEntry(@RequestParam(value="id") Integer id,
                              @RequestParam(value="expl") String expl) {
        Complaint complaint = complaintService.getComplaint(id);
        complaint.setState(Complaint.ComplaintState.WAIT_DECISION);
        complaint.setExplanatory(expl);
        complaintService.updateComplaint(complaint);
    }

}
