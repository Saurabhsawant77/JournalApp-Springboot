package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    //// http://localhost:8080/journal yeh url prr getMapping aur setMapping Dono pr jayega given URL prr

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll() { // http://localhost:8080/journal -> Get Asel request trr ikde yeil
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        // http://localhost:8080/journal -> Post Asel request trr ikde yeil
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myID}")
    public JournalEntry findById(@PathVariable ObjectId myID) {

        return journalEntryService.findById(myID).orElse(null);
    }

    @DeleteMapping("id/{myID}")
    public boolean deleteEntryById(@PathVariable ObjectId myID) {
        journalEntryService.deleteById(myID);
        return true;
    }

    @PutMapping("id/{myID}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myID, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(myID).orElse(null);
        if (old != null ){

            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;

    }


// Asa lihila trr chalnar nahi karan @GetMapping apn vrti ekda vparlay ani tyala specify path dili nahiye mg error yenar
//    @GetMapping
//    public List<JournalEntry> getAll(){ // http://localhost:8080/journal -> Get Asel request trr ikde yeil
//        return new ArrayList<>(journalEntries.values());
//    }


}
