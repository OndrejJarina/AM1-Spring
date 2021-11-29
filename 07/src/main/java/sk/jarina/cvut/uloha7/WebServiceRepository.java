package sk.jarina.cvut.uloha7;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.*;

@Repository
public class WebServiceRepository {

    private Map<Integer, Tour> tours = new TreeMap<Integer, Tour>();
    private ZonedDateTime lastModified;

    @PostConstruct
    private void initializeRepository() {
        this.lastModified = ZonedDateTime.now();
        Customer c1 = new Customer();
        c1.setName("Ondrej");
        c1.setSurname("Jarina");
        ArrayList<Customer> c1s = new ArrayList<Customer>();
        c1s.add(c1);
        Tour t1 = new Tour();
        t1.setName("Tour1");
        t1.setId(1);
        t1.setCustomers(c1s);
        tours.put(t1.getId(), t1);
    }

    public List<Tour> listAllTours() {
        return new ArrayList<>(this.tours.values());
    }

    public boolean addTour(Tour tour) {
        if (tours.containsKey(tour.getId())) {
            return false;
        }

        this.lastModified = ZonedDateTime.now();
        this.tours.put(tour.getId(), tour);
        return true;
    }

    public boolean addCustomerToTour(Customer customer, int tourId){
        if (!tours.containsKey(tourId)) {
            return false;
        }
        this.lastModified = ZonedDateTime.now();
        Tour tour = tours.get(tourId);
        ArrayList<Customer> customers = new ArrayList<Customer>(tour.getCustomers());
        customers.add(customer);
        tour.setCustomers(customers);
        tours.put(tour.getId(), tour);
        return true;
    }

    public Tour getTourById(int tourId){
        return this.tours.get(tourId);
    }

    public ZonedDateTime getLastModified(){
        return this.lastModified;
    }
}
