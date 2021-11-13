package sk.jarina.am1.uloha4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import uloha4.*;

@Endpoint
public class WebServiceEndpoint {


    @Autowired
    WebServiceClient client;

    @Autowired
    WebServiceRepository repository;

    //zobrazenie vsetkych paymentov
    @PayloadRoot(namespace = "uloha4", localPart = "getAllPaymentsRequest")
    @ResponsePayload
    public GetAllPaymentsResponse getAllPayments(@RequestPayload GetAllPaymentsRequest request) {
        GetAllPaymentsResponse response = new GetAllPaymentsResponse();
        response.getPayment().addAll(repository.listAllPayments());
        return response;
    }

    //vytvorenie noveho payment requestu
    @PayloadRoot(namespace = "uloha4", localPart = "addNewPaymentRequest")
    @ResponsePayload
    public AddNewPaymentResponse postNewPayment(@RequestPayload AddNewPaymentRequest request) {
        AddNewPaymentResponse response = new AddNewPaymentResponse();
        // ak validacia neprebehne uspesne, vrati hodnotu FAIL
        if (!client.validateCard(request.getCardOwner(), request.getCardNumber()).isResult()) {
            response.setResult(Result.FAIL);
            return response;
        }
        // ak validacia prebehne uspesne
        Payment addedPayment = repository.addPayment(request.getCardOwner(), request.getCardNumber());
        response.setResult(Result.SUCCESS);
        response.setPayment(addedPayment);
        return response;
    }

}
