package sk.jarina.cvut.uloha7;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    private int id;
    private String name;
    private List<Customer> customers;

    public Tour() {
        this.customers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
