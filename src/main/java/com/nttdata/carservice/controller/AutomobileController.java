package com.nttdata.carservice.controller;

import com.nttdata.carservice.errorhandler.AutomobileErrorHandler;
import com.nttdata.carservice.storage.AutomobileRepo;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import com.nttdata.carservice.entity.Automobile;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for Ultra-api.
 *
 * Handles all Rest calls. For docs call on <a href="http://localhost:8080/swagger-ui/#/">Swagger docs</a> during runtime
 * @author "Amos Gross"
 * @version 0.0.1
 */

@RestController
@RequestMapping("/ultra-api")
@EnableSwagger2
public class AutomobileController {
    //TODO: change input from Automobile to Hashmap (more intuitive) without Instantiation from new (to stay cool with DI)

    private final AutomobileDataStorage m_automobileDataStorage;

    private final AutomobileRepo automobileRepo;

    /**
     * Constructor for Controller.
     *
     * Inject dependency to DataStorage class into Controller.
     * @param automobileRepo injected Database Access Object
     */

    @Autowired
    public AutomobileController(AutomobileRepo automobileRepo, AutomobileDataStorage automobileDataStorage){
        this.m_automobileDataStorage = automobileDataStorage;
        this.automobileRepo = automobileRepo;
        automobileDataStorage.setM_automobileRepo(automobileRepo);
    }

    /**
     * Api call to get a car entry.
     *
     * @param id Id of Requested car
     * @return ResponseEntity with object and HTTP code
     */

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
            return AutomobileErrorHandler.wrongIdError();
        }
        return new ResponseEntity<>(m_automobileDataStorage.getAutomobileFromID(id), HttpStatus.OK);
    }

    /**
     * Api call to get all entries.
     *
     * @return ResponseEntity with all objects and HTTP code
     */

    @ApiOperation(
            value = "Get All Automobiles Info",
            notes = "Retrieve all available entries"
    )
    @GetMapping(value = "/read-all")
    public ResponseEntity<?> readAllAutomobile() {

        ArrayList<Automobile> allAutomobiles = m_automobileDataStorage.getM_allAutomobiles();

        if (allAutomobiles.isEmpty())
            return AutomobileErrorHandler.noEntriesError();
        return new ResponseEntity<>(allAutomobiles, HttpStatus.OK);
    }

    /**
     * Api call to update a single entry.
     *
     * Takes changes from two Automobile Objects and merges them. Excluding the id, the newer object's non null fields are always preferred.
     * @param automobileJson Automobile object, with all non-null parameters being the changes
     * @param id ID of car that has to be updated
     * @return ResponseEntity with HTTP code indicating its success or not
     */

    @ApiOperation(
            value = "Update Automobile Info by ID",
            notes = "Provide and ID to update that appropriate entry for your Car"
    )
    @PutMapping(value = "/update-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAutomobile(
            @ApiParam(value = "Info about Automobile",
            required = true,
            example = "{\n \"m_automobileAttributes\": {\n \"name\": \"Bob's car\",\n    \"model\": \"F150\"\n  }\n}")
            @RequestBody() Automobile automobileJson,
           @ApiParam(value = "ID of your car (returned at creation)",
                   required = true)
           @RequestParam(value = "id") int id) {

        if (m_automobileDataStorage.checkForInvalidID(id)) {
            return AutomobileErrorHandler.wrongIdError();
        }
        else if (automobileJson.getM_automobileAttributes().isEmpty()){
            return AutomobileErrorHandler.emptyAttributesError();
        }
        m_automobileDataStorage.changeAutomobile(id, automobileJson);
        return new ResponseEntity<>("Updated: " + id, HttpStatus.OK);
    }

    /**
     * Api call to add a new entry.
     *
     * @param automobileJSON Automobile Object containing info of new
     * @return Id of the new Entry
     */

    @ApiOperation(
            value = "Create a car entry",
            notes = "Provide a Car Object and add that to the entries"
    )
    @PostMapping(value = "/create-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAutomobile(@RequestBody Automobile automobileJSON) {
        if (automobileJSON.getM_automobileAttributes().isEmpty()){
            return AutomobileErrorHandler.emptyAttributesError();
        }
        m_automobileDataStorage.addAutomobile(automobileJSON);
        return new ResponseEntity<>("Created: " + automobileJSON.getM_id(), HttpStatus.CREATED);
    }

    /**
     * Api call to delete an entry.
     *
     * Takes an ID, checks it's validity and deletes the entry at that point.
     * @param id Id of the entry that has to be deleted
     * @return ResponseEntity with HTTP code indicating its success or not
     */

    @ApiOperation(
            value = "Delete a car entry",
            notes = "Provide a ID and remove that from the entries"
    )
    @ApiResponse(code = 204, message = "Success in deletion")
    @DeleteMapping(value = "/delete-car")
    public ResponseEntity<String> deleteAutomobile(
        @ApiParam(value = "ID of your car (returned at creation)", required = true)
        @RequestParam(value = "id") int id) {
        if (m_automobileDataStorage.checkForInvalidID(id)) {
            return AutomobileErrorHandler.wrongIdError();
        }
        m_automobileDataStorage.removeAutomobile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all entries.
     *
     * Resets the internal storage back to 0 entries.
     * @return ResponseEntity with 204 HTTP Response code
     */

    @ApiOperation(
            value = "Reset all entries",
            notes = "Reset all entries"
    )
    @ApiResponse(code = 204, message = "Entries have been reset")
    @DeleteMapping(value = "/reset")
    public ResponseEntity<?> resetAllAutomobiles(){
        m_automobileDataStorage.clearM_allAutomobiles();
        return new ResponseEntity<>("Empty Database", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> test(){
        Automobile automobile = new Automobile();
        automobile.setValue("name", "kkk");
        automobile.generateId(m_automobileDataStorage);
        automobileRepo.save(automobile);
        m_automobileDataStorage.addAutomobile(automobile);
        return new ResponseEntity<>("oof, it worked?", HttpStatus.OK);
    }

}
