package sk.jarina.cvut.uloha6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping(value = "/tour")
public class WebServiceController {

    @Autowired
    WebServiceRepository repository;

    @DeleteMapping(path = "/{tourId}")
    public ResponseEntity<EntityModel<MessageDTO>> deleteFromRepository(@PathVariable String tourId) throws InterruptedException {
        if (repository.isForDeletion(tourId) || repository.isDeleted(tourId)) {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Request to delete tour " + tourId + " was already sent!"),
                    linkTo(WebServiceController.class).slash(tourId).slash("status").withRel("GET")
            );
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else if (repository.isSaved(tourId)) {
            this.repository.deleteTour(tourId);
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " will be deleted"),
                    linkTo(WebServiceController.class).slash(tourId).slash("status").withRel("GET")
            );
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

        } else {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " was never in the repository"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "/{tourId}/status")
    public ResponseEntity<EntityModel<MessageDTO>> checkDeletionStatus(@PathVariable String tourId) {
        if (repository.isForDeletion(tourId)) {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " in the queue for deletion"));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (repository.isSaved(tourId)) {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " was not yet deleted"),
                    linkTo(WebServiceController.class).slash(tourId).withRel("DELETE")
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (repository.isDeleted(tourId)) {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " was already deleted"));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            EntityModel<MessageDTO> response = EntityModel.of(new MessageDTO("Tour " + tourId + " was never in the repository"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
