package beauty_app.service.rest;

import beauty_app.business_logic.Client;
import beauty_app.business_logic.Entry;
import beauty_app.business_logic.Master;
import beauty_app.business_logic.Service;
import beauty_app.database.ClientService;
import beauty_app.database.EntryService;
import beauty_app.database.MasterService;
import beauty_app.database.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class EntryController {
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService servicesService;
    @Autowired
    EntryService entryService;
    @Autowired
    ClientService clientService;

    @RequestMapping("/entry/free-time")
    public List<String> freeTime(@RequestParam(value="date",required = false) String date,
                                 @RequestParam(value="master",
                                         defaultValue="0") Integer masterId) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        List<String> time = getAllTime();
        if (date == null || masterId == 0) {
            return time;
        }
        Master master = masterService.getMaster(masterId);
        try {

            time = getFreeTime(master, time,timeFormat,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private List<String> getFreeTime(Master master, List<String> posibleTime,
                                     DateFormat format, String dateString)
            throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        List<Entry> masterEntries = entryService.getEntries(date,master);
        List<String> busyTimes = new ArrayList<>();

        for (Entry entry: masterEntries){
            for(String t : posibleTime){
                try {
                    Date tTime = format.parse(t);
                    if ((entry.getTime().compareTo(tTime)<=0)
                            &&(entry.countEntryEnd().compareTo(tTime)>0)){
                        busyTimes.add(t);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        posibleTime.removeAll(busyTimes);
        return posibleTime;
    }


    private List<String> getAllTime(){
        List<String> time = new ArrayList<>();
        for(int i = 10; i<= 18; i++){
            time.add(i + ":00");
            time.add(i + ":30");
        }
        return time;
    }

    @RequestMapping("/entry/masters")
    public Map<Integer,String> masters(@RequestParam(value="services",required = false)
                                            Integer[] selectedServicesId) {
        List<Master> masters;
        if (selectedServicesId == null) {
            masters = masterService.getMasters();
        }
        else {
            Service.ServiceType service = servicesService
                    .getService(selectedServicesId[0]).getType();
            masters = masterService.getMasters(service);
        }
        Map<Integer,String> mastersNames = new HashMap<>();
        for (Master master : masters) {
            mastersNames.put(master.getId(),master.toString());
        }
        return mastersNames;
    }

    @RequestMapping("/entry/services")
    public Map<Integer,String> services(@RequestParam(value="master",required = false)
                                         Integer masterId,
                                        @RequestParam(value="services",required = false)
                                                Integer[] servicesId) {
        List<Service> services;
        if (servicesId != null) {
            Service serviceForType = servicesService.getService(servicesId[0]);
            Service.ServiceType type = serviceForType.getType();
            services = servicesService.getServices(type);
        } else {
            if (masterId == null) {
                services = servicesService.getServices();
            } else {
                Master master = masterService.getMaster(masterId);
                services = servicesService.getServices(master.getServices());
            }
        }
        Map<Integer,String> servicesNames = new HashMap<>();
        for (Service service : services) {
            servicesNames.put(service.getId(),service.toString());
        }
        return servicesNames;
    }

    @RequestMapping("/entry/save")
    public void saveEntry(@RequestParam(value="date") String dateStr,
                          @RequestParam(value="time") String timeStr,
                          @RequestParam(value="master") Integer masterId,
                          @RequestParam(value="services") Integer[] servicesId,
                          @RequestParam(value="name") String clientName,
                          @RequestParam(value="phone") String clientPhone) throws ParseException {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateStr);
        Date time = timeFormat.parse(timeStr);
        Master master = masterService.getMaster(masterId);
        List<Service> services = servicesService.getServices(Arrays.asList(servicesId));
        Entry entry = new Entry(date, time, master,services,new Client(clientName, clientPhone));
        addEntry(entry);
    }

    private Boolean addEntry(Entry entry){
        if(entry.getClient().getId() == null) {
            Client cl = clientService.getClient(entry.getClient().getPhone());
            if (cl == null)
                cl = clientService.addClient(entry.getClient());
            if (cl == null)
                return false;
            entry.setClient(cl);
        }
        Entry savedEntry = entryService.addEntry(entry);
        if (savedEntry.getId() == 0)
            return false;
        entry.setId(savedEntry.getId());
        return true;
    }

}
