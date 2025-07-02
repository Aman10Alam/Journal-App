package net.engineeringdigest.journalApp.cache;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.respository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AppCache {

    public enum keys{
        weather_api;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCacheMap;

    @PostConstruct
    public void init(){
        appCacheMap=new HashMap<>();
        List<ConfigJournalAppEntity> all= configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity: all){
            appCacheMap.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
        log.info("New app cache initialized");
    }
}
