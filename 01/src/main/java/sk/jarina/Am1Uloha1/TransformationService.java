package sk.jarina.Am1Uloha1;

import org.springframework.stereotype.Service;

@Service
public class TransformationService {

    BookingInfo createBooking(String id, String location, String firstnameLastname) {
        String[] fullname = firstnameLastname.split(" ");
        return new BookingInfo(id, location, new Person(fullname[0], fullname[1]));
    }
}
