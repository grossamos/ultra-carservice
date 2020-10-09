package com.nttdata.carservice.controller;

import com.nttdata.carservice.AutomobileDataStorage;
import com.nttdata.carservice.Automobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/ultra-api")
public class AutomobileController {

    @Autowired
    private final AutomobileDataStorage m_automobileDataStorage;


    @Autowired
    public AutomobileController(AutomobileDataStorage automobileDataStorage){
        this.m_automobileDataStorage = automobileDataStorage;
        m_automobileDataStorage.getAutomobilesFromFile();
    }

    @RequestMapping(value = "/read-single")
    @GetMapping
    public ResponseEntity<?> readSingleAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (m_automobileDataStorage.checkForInvalidID(id))
            return incorrectParameterResponse();
        return new ResponseEntity<>(m_automobileDataStorage.getAutomobileFromID(id), HttpStatus.OK);
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<?> readAllAutomobile() {
        return new ResponseEntity<>(m_automobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

    @PutMapping(value = "/update-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAutomobile(@RequestBody String automobileJson,
                                                   @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || m_automobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }

        Automobile newAutomobile = Automobile.jsonToAutomobile(automobileJson);

        m_automobileDataStorage.changeAutomobile(id, newAutomobile);
        return new ResponseEntity<>("Updated: " + id, HttpStatus.OK);
    }

    @PostMapping(value = "/create-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAutomobile(@RequestBody String automobileJSON) {
        Automobile newAutomobile = Automobile.jsonToAutomobile(automobileJSON);
        newAutomobile.generateId(m_automobileDataStorage);
        m_automobileDataStorage.addAutomobile(newAutomobile);
        return new ResponseEntity<>("Created: " + newAutomobile.getM_id(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-car")
    public ResponseEntity<String> deleteAutomobile(@RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || m_automobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }
        m_automobileDataStorage.removeAutomobile(m_automobileDataStorage.getIndexFromId(id));
        return new ResponseEntity<>("Deleted: " + id, HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<String> incorrectParameterResponse() {
        return new ResponseEntity<>("<div class=\"tenor-gif-embed\" data-postid=\"4649061\" data-share-method=\"host\" data-width=\"100%\" data-aspect-ratio=\"1.8308823529411764\"><a href=\"https://tenor.com/view/hells-kitchen-gordon-ramsay-you-fucked-up-you-messed-up-gif-4649061\">You Fucked Up GIF</a> from <a href=\"https://tenor.com/search/hellskitchen-gifs\">Hellskitchen GIFs</a></div><script type=\"text/javascript\" async src=\"https://tenor.com/embed.js\"></script>", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/reset")
    public ResponseEntity<ArrayList<Automobile>> resetAllAutomobiles(){
        m_automobileDataStorage.clearM_allAutomobiles();
        return new ResponseEntity<>(m_automobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

}
