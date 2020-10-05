package com.nttdata.carservice.controller;

import com.nttdata.carservice.datastorage.AutomobileDataStorage;
import com.nttdata.carservice.Automobile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/ultra-api")
public class AutomobileController {

    @RequestMapping(value = "/read-single")
    @GetMapping
    public ResponseEntity<?> readSingleAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (AutomobileDataStorage.checkForInvalidID(id))
            return incorrectParameterResponse();
        return new ResponseEntity<>(AutomobileDataStorage.getAutomobileFromID(id), HttpStatus.OK);
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<?> readAllAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        return new ResponseEntity<>(AutomobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

    @PutMapping(value = "/create-car", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createAutomobile(@RequestBody Automobile automobile) {
        AutomobileDataStorage.addAutomobile(automobile);
        return new ResponseEntity<>("Added: " + automobile.getM_id(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/update-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAutomobile(@RequestBody Automobile automobile,
                                                   @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || AutomobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }

        AutomobileDataStorage.changeAutomobile(AutomobileDataStorage.getIndexFromId(id), automobile);
        return new ResponseEntity<>("Updated: " + automobile.getM_id(), HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete-car")
    public ResponseEntity<String> deleteAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || AutomobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }
        AutomobileDataStorage.removeAutomobile(AutomobileDataStorage.getIndexFromId(id));
        return new ResponseEntity<>("Deleted: " + id, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> incorrectParameterResponse() {
        return new ResponseEntity<>("<div class=\"tenor-gif-embed\" data-postid=\"4649061\" data-share-method=\"host\" data-width=\"100%\" data-aspect-ratio=\"1.8308823529411764\"><a href=\"https://tenor.com/view/hells-kitchen-gordon-ramsay-you-fucked-up-you-messed-up-gif-4649061\">You Fucked Up GIF</a> from <a href=\"https://tenor.com/search/hellskitchen-gifs\">Hellskitchen GIFs</a></div><script type=\"text/javascript\" async src=\"https://tenor.com/embed.js\"></script>", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/reset")
    public ResponseEntity<ArrayList<Automobile>> resetAllAutomobiles(){
        AutomobileDataStorage.clearM_allAutomobiles();
        return new ResponseEntity<>(AutomobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

}
