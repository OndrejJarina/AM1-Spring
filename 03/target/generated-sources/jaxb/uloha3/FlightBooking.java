//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.13 at 11:42:19 AM CET 
//


package uloha3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flightBooking complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flightBooking"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bookingId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="passenger" type="{uloha3}passenger"/&gt;
 *         &lt;element name="originAirport" type="{uloha3}airportIdentifier"/&gt;
 *         &lt;element name="departureDateTime" type="{uloha3}dateAndTime"/&gt;
 *         &lt;element name="arrivalDateTime" type="{uloha3}dateAndTime"/&gt;
 *         &lt;element name="destinationAirport" type="{uloha3}airportIdentifier"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flightBooking", propOrder = {
    "bookingId",
    "passenger",
    "originAirport",
    "departureDateTime",
    "arrivalDateTime",
    "destinationAirport"
})
public class FlightBooking {

    protected int bookingId;
    @XmlElement(required = true)
    protected Passenger passenger;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AirportIdentifier originAirport;
    @XmlElement(required = true)
    protected DateAndTime departureDateTime;
    @XmlElement(required = true)
    protected DateAndTime arrivalDateTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AirportIdentifier destinationAirport;

    /**
     * Gets the value of the bookingId property.
     * 
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the value of the bookingId property.
     * 
     */
    public void setBookingId(int value) {
        this.bookingId = value;
    }

    /**
     * Gets the value of the passenger property.
     * 
     * @return
     *     possible object is
     *     {@link Passenger }
     *     
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Sets the value of the passenger property.
     * 
     * @param value
     *     allowed object is
     *     {@link Passenger }
     *     
     */
    public void setPassenger(Passenger value) {
        this.passenger = value;
    }

    /**
     * Gets the value of the originAirport property.
     * 
     * @return
     *     possible object is
     *     {@link AirportIdentifier }
     *     
     */
    public AirportIdentifier getOriginAirport() {
        return originAirport;
    }

    /**
     * Sets the value of the originAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link AirportIdentifier }
     *     
     */
    public void setOriginAirport(AirportIdentifier value) {
        this.originAirport = value;
    }

    /**
     * Gets the value of the departureDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndTime }
     *     
     */
    public DateAndTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Sets the value of the departureDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndTime }
     *     
     */
    public void setDepartureDateTime(DateAndTime value) {
        this.departureDateTime = value;
    }

    /**
     * Gets the value of the arrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndTime }
     *     
     */
    public DateAndTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the value of the arrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndTime }
     *     
     */
    public void setArrivalDateTime(DateAndTime value) {
        this.arrivalDateTime = value;
    }

    /**
     * Gets the value of the destinationAirport property.
     * 
     * @return
     *     possible object is
     *     {@link AirportIdentifier }
     *     
     */
    public AirportIdentifier getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * Sets the value of the destinationAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link AirportIdentifier }
     *     
     */
    public void setDestinationAirport(AirportIdentifier value) {
        this.destinationAirport = value;
    }

}
