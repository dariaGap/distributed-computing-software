package beauty_app.database;

import beauty_app.business_logic.Complaint;
import beauty_app.business_logic.Entry;
import beauty_app.database.entities.ComplaintsEntity;
import beauty_app.database.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private EntryService entryService;

    private static Map<Integer, Complaint> loadedComplaints = new HashMap<>();

    private Complaint convertEntityToComplaint(Optional<ComplaintsEntity> entity1) {
        if (entity1.isPresent()) {
            ComplaintsEntity entity = entity1.get();

            Entry entry = entryService.getEntry(entity.getEntry());
            Complaint complaint = new Complaint(entity.getId(),entry,entity.getText(),
                    entity.getExplanatory(),entity.getDecisionMaster(),
                    entity.getDecisionClient(),
                    Complaint.ComplaintState.stringToComplaintState(entity.getState()));
            loadedComplaints.put(complaint.getId(), complaint);
            return complaint;
        }
        return null;
    }

    private ComplaintsEntity convertComplaintToEntity(Complaint complaint) {
        ComplaintsEntity entity = new ComplaintsEntity();
        if (complaint.getId() != null) {
            entity.setId(complaint.getId());
        }

        entity.setDecisionClient(complaint.getDecisionClient());
        entity.setDecisionMaster(complaint.getDecisionMaster());
        entity.setEntry(complaint.getEntry().getId());
        entity.setExplanatory(complaint.getExplanatory());
        entity.setText(complaint.getText());
        entity.setState(Complaint.ComplaintState.complaintStateToString(complaint.getState()));
        return entity;
    }

    private List<Complaint> convertEntitiesToComplaints(Iterable<ComplaintsEntity> entities) {
        List<Complaint> complaints = new ArrayList<>();
        entities.forEach(entity -> complaints.add(convertEntityToComplaint(Optional.of(entity))));
        return complaints;
    }

    @Transactional
    public Complaint addComplaint(Complaint complaint) {
        ComplaintsEntity entity = convertComplaintToEntity(complaint);
        entity = complaintRepository.save(entity);
        complaint.setId(entity.getId());
        loadedComplaints.put(complaint.getId(),complaint);
        return complaint;
    }

    @Transactional
    public Boolean updateComplaint(Complaint complaint) {
        Optional<ComplaintsEntity> entity = complaintRepository.findById(complaint.getId());
        if (entity.isPresent()) {
            addComplaint(complaint);
            return true;
        }
        return false;
    }

    @Transactional
    public Complaint getComplaint(Integer id) {
        Complaint complaint = loadedComplaints.get(id);
        if (complaint != null) {
            return complaint;
        }
        Optional<ComplaintsEntity> entity = complaintRepository.findById(id);
        return convertEntityToComplaint(entity);
    }


    @Transactional
    public List<Complaint> getComplaints(Complaint.ComplaintState state){
        return convertEntitiesToComplaints(complaintRepository.findAllByState(
                Complaint.ComplaintState.complaintStateToString(state)));
    }


    @Transactional
    public Boolean removeComplaint(Complaint complaint){
        Integer id = complaint.getId();
        complaintRepository.deleteById(id);
        loadedComplaints.remove(id);
        return !complaintRepository.existsById(id);
    }
}
