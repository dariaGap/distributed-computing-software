package beauty_app.database.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "entries", schema = "public", catalog = "beauty")
public class EntriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date entryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryTime;
    private int master;
    private String state;
    @ManyToMany
    @JoinTable (name="entry_service",
            joinColumns=@JoinColumn (name="entry"),
            inverseJoinColumns=@JoinColumn(name="service"))
    private List<ServicesEntity> services;
    private int client;

    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entrydate", nullable = false)
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Basic
    @Column(name = "entrytime", nullable = false)
    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    @Basic
    @Column(name = "master", nullable = false)
    public int getMaster() {
        return master;
    }

    public void setMaster(int master) {
        this.master = master;
    }

    @Basic
    @Column(name = "state", nullable = false, length = -1)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "services", nullable = false)
    public List<ServicesEntity> getServices() {
        return services;
    }

    public void setServices(List<ServicesEntity> services) {
        this.services = services;
    }

    @Basic
    @Column(name = "client", nullable = false)
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntriesEntity entity = (EntriesEntity) o;
        return id == entity.id &&
                master == entity.master &&
                client == entity.client &&
                Objects.equals(entryDate, entity.entryDate) &&
                Objects.equals(state, entity.state) &&
                Objects.equals(services, entity.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryDate, master, state, services, client);
    }
}
