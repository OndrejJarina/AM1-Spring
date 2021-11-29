package sk.jarina.cvut.uloha7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping(value = "/tours")
public class WebServiceController {

    @Autowired
    WebServiceRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Tour>>> getToursWeak(@RequestHeader(value = "If-None-Match", required = false) String matchEtag) throws NoSuchAlgorithmException {

        List<Tour> tours = this.repository.listAllTours();
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        tours.forEach(
                tour -> {
                    md5.update((byte) tour.getId());
                    md5.update(tour.getName().getBytes(StandardCharsets.UTF_8));
                }
        );
        byte[] checksumBytes = md5.digest();
        String entityTag = "W/" + String.format("%032X", new BigInteger(1, checksumBytes));
        if (entityTag.equals(matchEtag)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .eTag(entityTag)
                    .lastModified(repository.getLastModified())
                    .body(null);
        }
        CollectionModel<EntityModel<Tour>> toursWithLinks = CollectionModel.of(
                tours.stream().map(tour -> EntityModel.of(tour,
                                linkTo(WebServiceController.class)
                                        .slash(tour.getId())
                                        .slash("add-customer")
                                        .withRel("ADD")))
                        .collect(Collectors.toList()),
                linkTo(WebServiceController.class).slash("strong").withRel("LIST"),
                linkTo(WebServiceController.class).slash("last-modified").withRel("LIST"),
                linkTo(WebServiceController.class).slash("add-tour").withRel("ADD"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(entityTag)
                .lastModified(repository.getLastModified())
                .body(toursWithLinks);
    }

    @GetMapping(path = "/strong")
    public ResponseEntity<CollectionModel<EntityModel<Tour>>> getToursStrong(@RequestHeader(value = "If-None-Match", required = false) String matchEtag) throws NoSuchAlgorithmException {

        List<Tour> tours = this.repository.listAllTours();
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        tours.forEach(
                tour -> {
                    md5.update((byte) tour.getId());
                    md5.update(tour.getName().getBytes(StandardCharsets.UTF_8));
                    tour.getCustomers().forEach(
                            customer -> {
                                md5.update(customer.getName().getBytes(StandardCharsets.UTF_8));
                                md5.update(customer.getSurname().getBytes(StandardCharsets.UTF_8));
                            }
                    );
                }
        );
        byte[] checksumBytes = md5.digest();
        String entityTag = String.format("%032X", new BigInteger(1, checksumBytes));

        CollectionModel<EntityModel<Tour>> toursWithLinks = CollectionModel.of(
                tours.stream().map(tour -> EntityModel.of(tour,
                                linkTo(WebServiceController.class)
                                        .slash(tour.getId())
                                        .slash("add-customer")
                                        .withRel("ADD")))
                        .collect(Collectors.toList()),
                linkTo(WebServiceController.class).withRel("LIST"),
                linkTo(WebServiceController.class).slash("last-modified").withRel("LIST"),
                linkTo(WebServiceController.class).slash("add-tour").withRel("ADD"));

        if (entityTag.equals(matchEtag)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .eTag(entityTag)
                    .lastModified(repository.getLastModified())
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(entityTag)
                .lastModified(repository.getLastModified())
                .body(toursWithLinks);
    }

    @GetMapping(path = "/last-modified")
    public ResponseEntity<CollectionModel<EntityModel<Tour>>> getToursLastModified(@RequestHeader(value = "If-Modified-Since", required = false) String modifiedDate) throws NoSuchAlgorithmException {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");
        ZonedDateTime modifiedSince;
        try {
            modifiedSince = ZonedDateTime.parse(modifiedDate, format);
        } catch (DateTimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .lastModified(repository.getLastModified())
                    .body(null);
        }

        if (!modifiedSince.isBefore(repository.getLastModified())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .lastModified(repository.getLastModified())
                    .body(null);
        }

        List<Tour> tours = this.repository.listAllTours();

        CollectionModel<EntityModel<Tour>> toursWithLinks = CollectionModel.of(
                tours.stream().map(tour -> EntityModel.of(tour,
                                linkTo(WebServiceController.class)
                                        .slash(tour.getId())
                                        .slash("add-customer")
                                        .withRel("ADD")))
                        .collect(Collectors.toList()),
                linkTo(WebServiceController.class).slash("strong").withRel("LIST"),
                linkTo(WebServiceController.class).withRel("LIST"),
                linkTo(WebServiceController.class).slash("add-tour").withRel("ADD"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(repository.getLastModified())
                .body(toursWithLinks);
    }

    @PostMapping(path = "/add-tour")
    public ResponseEntity<EntityModel<Tour>> addTour(@RequestBody Tour tour) {
        if (!this.repository.addTour(tour)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        EntityModel<Tour> tourWithLinks = EntityModel.of(tour,
                linkTo(WebServiceController.class).withRel("LIST"),
                linkTo(WebServiceController.class).slash("strong").withRel("LIST"),
                linkTo(WebServiceController.class).slash("last-modified").withRel("LIST"),
                linkTo(WebServiceController.class).slash(tour.getId()).slash("add-customer").withRel("ADD")

        );

        return ResponseEntity.status(HttpStatus.OK).body(tourWithLinks);

    }

    @PostMapping(path = "/{tourId}/add-customer")
    public ResponseEntity<EntityModel<Tour>> addCustomer(@PathVariable int tourId, @RequestBody Customer customer) {
        System.out.println(customer.getName());
        if (!this.repository.addCustomerToTour(customer, tourId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Tour tour = this.repository.getTourById(tourId);
        EntityModel<Tour> tourWithLinks = EntityModel.of(tour,
                linkTo(WebServiceController.class).withRel("LIST"),
                linkTo(WebServiceController.class).slash("strong").withRel("LIST"),
                linkTo(WebServiceController.class).slash("last-modified").withRel("LIST"),
                linkTo(WebServiceController.class).slash("add-tour").withRel("ADD")
        );
        return ResponseEntity.status(HttpStatus.OK).body(tourWithLinks);

    }
}
