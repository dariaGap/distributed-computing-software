package beauty_app.business_logic;

import beauty_app.database.ClientService;
import beauty_app.database.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import beauty_app.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Entry {
    public enum EntryState {
        OPENED, EXECUTING, CLOSED, CANCELED;

        public static String entryStateToString(EntryState st){
            switch (st){
                case OPENED:
                    return "OPENED";
                case EXECUTING:
                    return "EXECUTING";
                case CLOSED:
                    return "CLOSED";
                case CANCELED:
                    return "CANCELED";
                default:
                    return null;
            }
        }

        public static EntryState stringToEntryState(String st){
            switch (st){
                case "OPENED":
                    return OPENED;
                case "EXECUTING":
                    return EXECUTING;
                case "CLOSED":
                    return CLOSED;
                case "CANCELED":
                    return CANCELED;
                default:
                    return null;
            }
        }
    }

    private Integer id;
    private Date date;
    private Date time;
    private Master master;
    private List<Service> services;
    private Client client;
    private EntryState state;

    public Entry(Integer id, Date  date, Date time,
                 Master master, List<Service> services,
                 Client client, EntryState state){
        this.id = id;
        this.date = date;
        this.time = time;
        this.master = master;
        this.services = services;
        this.client = client;
        this.state = state;
    }

    public Entry(Date date, Date time, Master master,
                 List<Service> services, Client client, EntryState state){
        this.date = date;
        this.time = time;
        this.master = master;
        this.services = services;
        this.client = client;
        this.state = state;
    }

    public Entry(Date date, Date time, Master master, List<Service> services, Client client){
        this.date = date;
        this.time = time;
        this.master = master;
        this.services = services;
        this.client = client;
        this.state = EntryState.OPENED;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time = time;
    }

    public Master getMaster(){
        return master;
    }

    public List<Service> getServices(){
        return services;
    }

    public Client getClient(){
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public EntryState getState(){
        return state;
    }

    public void setState(EntryState state){
        this.state = state;
    }

    public Date countEntryEnd(){
        Date endTime = this.time;
        for(Service s: services){
            endTime = Util.addMinutes(endTime,s.getLength());
        }
        return endTime;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        String date = "Время: " + format.format(this.date.getTime());
        String master = "мастер: " + this.master;
        String client = "клиент: " + this.client;
        return date + "; " + client + "; " + master;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return id.equals(entry.getId()) &&
                date.getTime() == entry.getDate().getTime() &&
                time.getTime() == entry.getTime().getTime() &&
                Objects.equals(master, entry.master) &&
                Objects.equals(services, entry.services) &&
                Objects.equals(client, entry.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, master, services, client);
    }
}
