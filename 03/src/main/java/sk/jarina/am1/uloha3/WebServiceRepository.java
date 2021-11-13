package sk.jarina.am1.uloha3;

import org.springframework.stereotype.Repository;
import uloha3.AirportIdentifier;
import uloha3.DateAndTime;
import uloha3.FlightBooking;
import uloha3.Passenger;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class WebServiceRepository {
    private final ArrayList<FlightBooking> flightBookings = new ArrayList<FlightBooking>();

    @PostConstruct
    public void initializeRepository() {
        Passenger p1 = new Passenger();
        p1.setFirstname("Ondrej");
        p1.setLastname("Jarina");
        p1.setPassport("AH37723SM");
        FlightBooking f1 = new FlightBooking();
        f1.setBookingId(1);
        f1.setPassenger(p1);
        f1.setOriginAirport(AirportIdentifier.BTS);
        f1.setDestinationAirport(AirportIdentifier.PRG);
        DateAndTime d1a = new DateAndTime();
        d1a.setDay(1);
        d1a.setMonth(2);
        d1a.setHour(14);
        d1a.setMinute(30);
        d1a.setYear(2021);
        f1.setDepartureDateTime(d1a);
        DateAndTime d1b = new DateAndTime();
        d1b.setDay(1);
        d1b.setMonth(2);
        d1b.setHour(19);
        d1b.setMinute(30);
        d1b.setYear(2021);
        f1.setArrivalDateTime(d1b);

        Passenger p2 = new Passenger();
        p2.setFirstname("Jozko");
        p2.setLastname("Mrkvicka");
        p1.setPassport("WW99912PP");
        FlightBooking f2 = new FlightBooking();
        f2.setBookingId(2);
        f2.setPassenger(p2);
        f2.setOriginAirport(AirportIdentifier.KSC);
        f2.setDestinationAirport(AirportIdentifier.SYD);
        DateAndTime d2a = new DateAndTime();
        d2a.setDay(3);
        d2a.setMonth(12);
        d2a.setHour(18);
        d2a.setMinute(45);
        d2a.setYear(2021);
        f2.setDepartureDateTime(d2a);
        DateAndTime d2b = new DateAndTime();
        d2b.setDay(4);
        d2b.setMonth(12);
        d2b.setHour(17);
        d2b.setMinute(25);
        d2b.setYear(2021);
        f2.setArrivalDateTime(d2b);
        flightBookings.add(f1);
        flightBookings.add(f2);
    }

    //pridanie bookingu letu
    public boolean addFlightBooking(FlightBooking f) {
        Random random = new Random();
        //vytvorenie nahodneho identifikatoru
        f.setBookingId((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));
        return flightBookings.add(f);
    }

    public List<FlightBooking> getAllFlightBookings() {
        return this.flightBookings;
    }

    public boolean removeFlightBooking(int bookingId) {
        //vrati true, ak sa podari odstranit, false ak zadane ID neexistuje
        return flightBookings.removeIf(flightBooking -> flightBooking.getBookingId() == bookingId);
    }

    public boolean updateFlightBooking(FlightBooking f, int bookingId) {

        //update bookingu, najde v arrayliste a zmeni, vrati false ak booking id nenajde
        for (int i = 0; i < flightBookings.size(); i++) {
            if (flightBookings.get(i).getBookingId() == bookingId) {
                f.setBookingId(bookingId);
                flightBookings.set(i, f);
                return true;
            }
        }
        return false;
    }

}
