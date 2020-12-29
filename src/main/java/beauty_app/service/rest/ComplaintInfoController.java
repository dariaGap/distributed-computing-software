package beauty_app.service.rest;

import beauty_app.business_logic.Complaint;
import beauty_app.business_logic.Master;
import beauty_app.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ComplaintInfoController {
    @Autowired
    MasterService masterService;
    @Autowired
    ComplaintService complaintService;

    @RequestMapping("/complaintInfo")
    public Complaint complaintInfo(
            @RequestParam(value="id") Integer entryId) {
        Complaint complaint = complaintService.getComplaint(entryId);
        return complaint;
    }

    @RequestMapping("/complaints")
    public Map<Integer,String> getComplaints(@RequestParam(value="state")
                                                         String state) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> userRoles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        Map<Integer,String> result = new HashMap<>();
        List<Complaint> complaints;
        Complaint.ComplaintState complaintState =
                Complaint.ComplaintState.stringToComplaintState(state);
        if (userRoles.contains("ADMIN")) {
            complaints = complaintService.getComplaints(complaintState);
            for (Complaint complaint : complaints) {
                result.put(complaint.getId(),complaint.toString());
            }
        } else {
            Master master = masterService.getMaster(auth.getName());
            complaints = complaintService.getComplaints(complaintState);
            for (Complaint complaint : complaints) {
                if (complaint.getEntry().getMaster().getId()
                        .equals(master.getId())) {
                    result.put(complaint.getId(), complaint.toString());
                }
            }
        }
        return result;
    }
}
