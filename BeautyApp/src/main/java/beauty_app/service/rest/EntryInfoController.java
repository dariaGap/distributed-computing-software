package beauty_app.service.rest;

import beauty_app.business_logic.Entry;
import beauty_app.business_logic.Master;
import beauty_app.database.ClientService;
import beauty_app.database.EntryService;
import beauty_app.database.MasterService;
import beauty_app.database.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class EntryInfoController {
    @Autowired
    MasterService masterService;
    @Autowired
    ServicesService servicesService;
    @Autowired
    EntryService entryService;
    @Autowired
    ClientService clientService;

    @RequestMapping("/entryInfo")
    public Entry entryInfo(
            @RequestParam(value="id") Integer entryId) {
        Entry entry = entryService.getEntry(entryId);
        return entry;
    }

    @RequestMapping("/entries")
    public Map<Integer,String> getEntries(@RequestParam(value="date") String dateString,
                                          @RequestParam(value="state",
            required = false) String state) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> userRoles = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<Integer,String> result = new HashMap<>();
        try {
            Date date = dateFormat.parse(dateString);
            List<Entry> entries;
            if (userRoles.contains("ADMIN")) {
                entries = entryService.getEntries(date,state);
            } else {
                Master master = masterService.getMaster(auth.getName());
                entries = entryService.getEntries(date,
                        Entry.EntryState.EXECUTING,master);
            }
            for (Entry entry : entries) {
                result.put(entry.getId(),entry.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
