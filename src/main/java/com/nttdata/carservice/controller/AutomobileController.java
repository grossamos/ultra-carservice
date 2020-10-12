package com.nttdata.carservice.controller;

import com.nttdata.carservice.AutomobileDataStorage;
import com.nttdata.carservice.Automobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping("/ultra-api")
public class AutomobileController {

    //TODO: change input from Automobile to Hashmap (more intuitive) without Instantiation from new (to stay cool with DI)

    @Autowired
    private final AutomobileDataStorage m_automobileDataStorage;

    private static final Logger logger = Logger.getLogger(AutomobileController.class.getName());


    @Autowired
    public AutomobileController(AutomobileDataStorage automobileDataStorage){
        this.m_automobileDataStorage = automobileDataStorage;
        m_automobileDataStorage.getAutomobilesFromFile();
    }

    @RequestMapping(value = "/read-single")
    @GetMapping
    public ResponseEntity<?> readSingleAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (m_automobileDataStorage.checkForInvalidID(id)){
            return incorrectParameterResponse();
        }
        logger.info("Retrieving info from: ID=" + id);
        return new ResponseEntity<>(m_automobileDataStorage.getAutomobileFromID(id), HttpStatus.OK);
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<?> readAllAutomobile() {
        logger.info("Retrieving info from: ID=ALL");
        return new ResponseEntity<>(m_automobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

    @PutMapping(value = "/update-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAutomobile(@RequestBody Automobile automobileJson,
                                                   @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || m_automobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }
        logger.info("Updating info from: ID=" + id);
        m_automobileDataStorage.changeAutomobile(id, automobileJson);
        return new ResponseEntity<>("Updated: " + id, HttpStatus.OK);
    }

    @PostMapping(value = "/create-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAutomobile(@RequestBody Automobile automobileJSON) {
        m_automobileDataStorage.addAutomobile(automobileJSON);
        logger.info("Creating Automobile at: ID=" + automobileJSON.getM_id());
        return new ResponseEntity<>("Created: " + automobileJSON.getM_id(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-car")
    public ResponseEntity<String> deleteAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || m_automobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }
        m_automobileDataStorage.removeAutomobile(id);
        logger.info("Deleted Entry: ID=" + id);
        return new ResponseEntity<>("Deleted: " + id, HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<String> incorrectParameterResponse() {
        logger.warning("OOps, wrong parameter");
        return new ResponseEntity<>("<div class=\"tenor-gif-embed\" data-postid=\"4649061\" data-share-method=\"host\" data-width=\"100%\" data-aspect-ratio=\"1.8308823529411764\"><a href=\"https://tenor.com/view/hells-kitchen-gordon-ramsay-you-fucked-up-you-messed-up-gif-4649061\">You Fucked Up GIF</a> from <a href=\"https://tenor.com/search/hellskitchen-gifs\">Hellskitchen GIFs</a></div><script type=\"text/javascript\" async src=\"https://tenor.com/embed.js\"></script>", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/reset")
    public ResponseEntity<?> resetAllAutomobiles(){
        m_automobileDataStorage.clearM_allAutomobiles();
        logger.info("Reset all");
        return new ResponseEntity<>("Empty Database", HttpStatus.NO_CONTENT);
    }

    public static Logger getLogger() {
        return logger;
    }
}
