package sk.jarina.am1.uloha4;

import org.springframework.stereotype.Repository;
import uloha4.Payment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class WebServiceRepository {
    private final ArrayList<Payment> payments = new ArrayList<Payment>();

    @PostConstruct
    public void initializeRepository(){
        Payment p1 = new Payment();
        p1.setCardNumber("1921-1362-1322-2344");
        p1.setCardOwner("Ondrej Jarina");
        p1.setOrderIdentifier(UUID.randomUUID().toString());
        payments.add(p1);
        Payment p2 = new Payment();
        p2.setCardNumber("8934-3124-3454-3455");
        p2.setCardOwner("Druhy uzivatel");
        p2.setOrderIdentifier(UUID.randomUUID().toString());
        payments.add(p2);
    }

    //pridanie noveho paymentu
    public Payment addPayment(String cardOwner, String cardNumber) {
        Payment payment = new Payment();
        payment.setCardOwner(cardOwner);
        payment.setCardNumber(cardNumber);

        // nahodne sa vygeneruje unikatny identifikator platby
        payment.setOrderIdentifier(UUID.randomUUID().toString());
        payments.add(payment);
        return payment;
    };

    // vypis vsetkych paymentov
    public ArrayList<Payment> listAllPayments(){
        return this.payments;
    }
}
