package sk.jarina.Am1Uloha1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Scanner;

@RestController
public class TransformationController {

    private final TransformationService transformationService;

    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @PostMapping(path = "/transform", consumes = "text/plain", produces = "application/json")
    ResponseEntity<BookingInfo> transformMessage(@RequestBody String originalMessage){
        ArrayList<String> bookingData = new ArrayList<String>();
        String[] messageSplit = originalMessage.split("===");
        if(messageSplit.length < 3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Scanner scanner = new Scanner(messageSplit[1]);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] array = s.split(": ");
            if (array.length > 1)
            {bookingData.add(array[1].replaceAll("\"", ""));}
        }
        BookingInfo response = transformationService.createBooking(bookingData.get(0), bookingData.get(1), bookingData.get(2) );
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
