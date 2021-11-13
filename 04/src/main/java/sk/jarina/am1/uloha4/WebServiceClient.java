package sk.jarina.am1.uloha4;

import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardRequest;
import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class WebServiceClient extends WebServiceGatewaySupport {

    // validacia karty
    public ValidateCardResponse validateCard(String cardOwner, String cardNumber){
        ValidateCardRequest request = new ValidateCardRequest();
        //overenie podla cisla karty a mena vlastnika
        request.setCardNumber(cardNumber);
        request.setCardOwner(cardOwner);
        return (ValidateCardResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
