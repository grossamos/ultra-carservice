package com.nttdata.carservice.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AutomobileErrorHandler {
    public static ResponseEntity<String> wrongIdError(){
        return new ResponseEntity<>("Id is invalid", HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<String> noEntriesError(){
        return new ResponseEntity<>("No Entries", HttpStatus.NO_CONTENT);
    }
    public static ResponseEntity<String> emptyAttributesError(){
        return new ResponseEntity<>("Please supply one or more attributes", HttpStatus.BAD_REQUEST);
    }
}
