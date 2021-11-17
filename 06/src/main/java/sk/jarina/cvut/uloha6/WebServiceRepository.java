package sk.jarina.cvut.uloha6;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class WebServiceRepository {

    private Map<String,Tour> savedTours = Collections.synchronizedMap(new HashMap<String,Tour>());
    private List<String> deletedTours = Collections.synchronizedList(new ArrayList<String>());
    private Map<String,Tour> forDeletionTours = Collections.synchronizedMap(new HashMap<String,Tour>());

    @PostConstruct
    void initializeRepository(){
        this.savedTours.put("1",new Tour("1", "Ondrej Jarina", "Slovakia"));
        this.savedTours.put("2",new Tour("2", "Test Druhy", "Czechia"));
        this.savedTours.put("3",new Tour("3", "Test Treti", "Poland"));
        this.savedTours.put("4",new Tour("4", "Test Stvrty", "Ukraine"));
    }

    @Async
    public void deleteTour(String tourId) throws InterruptedException {
        Tour toDelete = savedTours.get(tourId);
        forDeletionTours.put(toDelete.getId(), toDelete);
        Thread.sleep(15000);
        savedTours.remove(toDelete.getId());
        deletedTours.add(toDelete.getId());
        forDeletionTours.remove(toDelete.getId());
    }

    public boolean isSaved(String tourId){
        return savedTours.containsKey(tourId);
    }

    public boolean isForDeletion(String tourId){
        return forDeletionTours.containsKey(tourId);
    }

    public boolean isDeleted(String tourId){
        return deletedTours.contains(tourId);
    }



}
