package sk.jarina.am1.uloha3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import uloha3.*;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    //GET vsetkych bookingov
    @PayloadRoot(namespace = "uloha3", localPart = "getFlightBookingsRequest")
    @ResponsePayload
    public GetFlightBookingsResponse getFlightBookings(@RequestPayload GetFlightBookingsRequest request) {
        GetFlightBookingsResponse response = new GetFlightBookingsResponse();
        response.getFlightBooking().addAll(repository.getAllFlightBookings());
        return response;
    }

    //POST noveho bookingu
    @PayloadRoot(namespace = "uloha3", localPart = "addFlightBookingRequest")
    @ResponsePayload
    public AddFlightBookingResponse postFlightBooking(@RequestPayload AddFlightBookingRequest request) {
        AddFlightBookingResponse response = new AddFlightBookingResponse();
        boolean saved = repository.addFlightBooking(request.getFlightBooking());
        if (saved) {
            response.setResult(Result.SUCCESS);
            response.setFlightBooking(request.getFlightBooking());
        } else {
            response.setResult(Result.FAIL);
        }
        return response;
    }

    //delete bookingu na zaklade bookingID
    @PayloadRoot(namespace = "uloha3", localPart = "deleteFlightBookingRequest")
    @ResponsePayload
    public DeleteFlightBookingResponse deleteFlightBooking(@RequestPayload DeleteFlightBookingRequest request) {
        DeleteFlightBookingResponse response = new DeleteFlightBookingResponse();
        response.setBookingId(request.getBookingId());
        boolean deleted = repository.removeFlightBooking(request.getBookingId());
        if (!deleted) {
            response.setResult(Result.FAIL);
            return response;
        }
        response.setResult(Result.SUCCESS);

    return response;
    }

    // update bookingu, updatovany je booking na zaklade booking Id
    @PayloadRoot(namespace = "uloha3", localPart = "updateFlightBookingRequest")
    @ResponsePayload
    public UpdateFlightBookingResponse updateFlightBooking(@RequestPayload UpdateFlightBookingRequest request) {
        UpdateFlightBookingResponse response = new UpdateFlightBookingResponse();
        response.setBookingId(request.getFlightBooking().getBookingId());

        // ak nebol zadany booking ID, vrati FAIL
        if (request.getFlightBooking().getBookingId() == 0) {
            response.setResult(Result.FAIL);
            return response;
        }
        boolean updated = repository.updateFlightBooking(request.getFlightBooking(), request.getFlightBooking().getBookingId());
        if (updated) {
            response.setResult(Result.SUCCESS);
            response.setFlightBooking(request.getFlightBooking());
        } else {
            response.setResult(Result.FAIL);
        }
        return response;
    }
}
