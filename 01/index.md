#Domáca úloha 1
## Integration issues, Transformation of formats
### Ovládanie
Text sa odosiela v tele HTTP požiadavky na adresu _localhost:{port}/transform_.
Text môže byť odoslaný len v nasledovnom formáte:

**Formát textu**

    Dear Sir or Madam,
    
    please find the details about my booking below:
    
    ===
    Tour id: "1"
    Location: "Bohemian Switzerland"
    Person: "Jan Novak"
    ===
    
    Regards,
    Jan Novak

Transformovaný text sa vráti ako odpoveď v JSON štruktúre.

**Formát odpovede**

    {
    "id": "1",
    "location": "Bohemian Switzerland",
    "person": {
    "name": "Jan",
    "surname": "Novak"
        }
    }

### Implementácia

Zdrojový kód je rozdelený do tried:

**Person**
* Java objekt, reprezentuje štruktúru Person (osobu z textu)

**BookingInfo**
* Java objekt, ktorý reprezentuje informácie o rezervácii

**TransformationController**: 
* rest controller
* prijíma text z HTTP požiadavky, spracuje ho a odošle transformovanú odpoveď.

**TransformationService**:
* má jednu metódu createBooking, ktorá vytvorí Java objekty Person a BookingInfo, ktoré odošle do triedy TransformationController

Trieda Transformation controller odošle Java objekty, ktoré sa vďaka anotácii GetMapping(produces="application/json") skonvertujú na JSON objekty