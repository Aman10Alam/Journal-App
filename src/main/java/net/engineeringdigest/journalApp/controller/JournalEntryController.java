package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    public static String provideUserName(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUserName(userName);
        List<JournalEntry> entries= user.getJournalEntries();
       if(entries!=null && !entries.isEmpty()){
           return new ResponseEntity<>(entries,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/enter-journal")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName= authentication.getName();
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("find-journal/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable("id") ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUserName(userName);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); // to ensure the user can access only its own journals
        if(!collect.isEmpty()){
            JournalEntry journalEntry= journalEntryService.findById(myId).orElse(null);
            if(journalEntry != null){
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-journal/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable("id") ObjectId id) {
        String userName= provideUserName();
        boolean removed =journalEntryService.deleteById(id,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update-journal/{id}")
    public ResponseEntity<JournalEntry> updateJournalById( @PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        String userName = provideUserName();
        User user= userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
            if(oldEntry !=null){
                oldEntry.setTitle(newEntry.getTitle().equals("") ? oldEntry.getTitle() : newEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() == null || newEntry.getContent().equals("") ? oldEntry.getContent() : newEntry.getContent());
                oldEntry.setDate(LocalDateTime.now());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

//controller->service->repository
