package com.nttdata.carservice.controller;

import com.nttdata.carservice.AutomobileDataStorage;
import com.nttdata.carservice.Automobile;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.logging.Logger;

//Access docs at http://localhost:8080/swagger-ui/#/

@RestController
@RequestMapping("/ultra-api")
@EnableSwagger2
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

    @GetMapping(value = "/read-single")
    @ApiOperation(
            value = "Get Automobile Info by ID",
            notes = "Provide and ID to get the appropriate entry for your Car",
            response = Automobile.class
    )
    public ResponseEntity<?> readSingleAutomobile(
            @ApiParam(value = "ID of your car (returned at creation)", required = true)
            @RequestParam(value = "id") int id) {
        if (m_automobileDataStorage.checkForInvalidID(id)){
            return incorrectParameterResponse();
        }
        logger.info("Retrieving info from: ID=" + id);
        return new ResponseEntity<>(m_automobileDataStorage.getAutomobileFromID(id), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get All Automobiles Info",
            notes = "Retrieve all available entries"
    )
    @GetMapping(value = "/read-all")
    public ResponseEntity<HashMap<Integer, Automobile>> readAllAutomobile() {
        logger.info("Retrieving info from: ID=ALL");
        return new ResponseEntity<>(m_automobileDataStorage.getM_allAutomobiles(), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Update Automobile Info by ID",
            notes = "Provide and ID to update that appropriate entry for your Car"
    )
    @PutMapping(value = "/update-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAutomobile(
            @RequestBody Automobile automobileJson,
           @ApiParam(value = "ID of your car (returned at creation)", required = true)
           @RequestParam(value = "id", defaultValue = "0") int id) {
        if (id == 0 || m_automobileDataStorage.checkForInvalidID(id)) {
            return incorrectParameterResponse();
        }
        logger.info("Updating info from: ID=" + id);
        m_automobileDataStorage.changeAutomobile(id, automobileJson);
        return new ResponseEntity<>("Updated: " + id, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Create a car entry",
            notes = "Provide a Car Object and add that to the entries"
    )
    @PostMapping(value = "/create-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAutomobile(@RequestBody Automobile automobileJSON) {
        m_automobileDataStorage.addAutomobile(automobileJSON);
        logger.info("Creating Automobile at: ID=" + automobileJSON.getM_id());
        return new ResponseEntity<>("Created: " + automobileJSON.getM_id(), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Delete a car entry",
            notes = "Provide a ID and remove that from the entries"
    )
    @ApiResponse(code = 204, message = "Success in deletion")
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

    @ApiOperation(
            value = "Reset all entries",
            notes = "Reset all entries"
    )
    @ApiResponse(code = 204, message = "Entries have been reset")
    @DeleteMapping(value = "/reset")
    public ResponseEntity<?> resetAllAutomobiles(){
        m_automobileDataStorage.clearM_allAutomobiles();
        logger.info("Reset all");
        return new ResponseEntity<>("Empty Database", HttpStatus.NO_CONTENT);
    }

    public static Logger getLogger() {
        return logger;
    }
}
