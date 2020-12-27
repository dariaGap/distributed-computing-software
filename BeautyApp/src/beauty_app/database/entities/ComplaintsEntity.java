package beauty_app.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "complaints", schema = "public", catalog = "beauty")
public class ComplaintsEntity {
    private int id;
    private int entry;
    private String state;
    private String text;
    private String explanatory;
    private String decisionClient;
    private String decisionMaster;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entry", nullable = false)
    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "text", nullable = false, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "explanatory", nullable = true, length = -1)
    public String getExplanatory() {
        return explanatory;
    }

    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }

    @Basic
    @Column(name = "decision_client", nullable = true, length = -1)
    public String getDecisionClient() {
        return decisionClient;
    }

    public void setDecisionClient(String decisionClient) {
        this.decisionClient = decisionClient;
    }

    @Basic
    @Column(name = "decision_master", nullable = true, length = -1)
    public String getDecisionMaster() {
        return decisionMaster;
    }

    public void setDecisionMaster(String decisionMaster) {
        this.decisionMaster = decisionMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintsEntity that = (ComplaintsEntity) o;
        return id == that.id &&
                Objects.equals(state, that.state) &&
                Objects.equals(text, that.text) &&
                Objects.equals(explanatory, that.explanatory) &&
                Objects.equals(decisionClient, that.decisionClient) &&
                Objects.equals(decisionMaster, that.decisionMaster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, text, explanatory, decisionClient, decisionMaster);
    }
}
