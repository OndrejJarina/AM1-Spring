package sk.jarina.cvut.uloha6;

public class Tour {

    private String id;
    private String tourGuideName;
    private String country;

    public Tour(String id, String tourGuideName, String country) {
        this.id = id;
        this.tourGuideName = tourGuideName;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTourGuideName() {
        return tourGuideName;
    }

    public void setTourGuideName(String tourGuideName) {
        this.tourGuideName = tourGuideName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
