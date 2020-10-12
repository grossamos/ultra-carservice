package com.nttdata.carservice.controller;

import com.nttdata.carservice.Automobile;
import com.nttdata.carservice.AutomobileDataStorage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;


class AutomobileControllerTest {

    static AutomobileController automobileController;
    static AutomobileDataStorage automobileDataStorage;

    @BeforeAll
    static void setAutomobileController(){
        //initialize Controller and Mock Database
        automobileDataStorage = Mockito.mock(AutomobileDataStorage.class);
        automobileController = new AutomobileController(automobileDataStorage);

        //disable Logger
        AutomobileController.getLogger().setLevel(Level.OFF);
    }

    @Test
    void readSingleAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(AutomobileController.incorrectParameterResponse(), automobileController.readSingleAutomobile(0));

        //setup Mockito checkForInvalidID to say every ID is valid, and return empty Automobile at getAutomobileFromID
        Automobile emptyAutomobile = new Automobile();
        setMockIdCheckToFalse();
        Mockito.when(automobileDataStorage.getAutomobileFromID(Mockito.anyInt())).thenReturn(emptyAutomobile);

        //test if Controller returns empty Automobile as Response code, with correct code
        assertEquals(new ResponseEntity<>(emptyAutomobile, HttpStatus.OK), automobileController.readSingleAutomobile(0));
    }

    @Test
    void readAllAutomobile() {
        //setup mockito to return empty Hashmap
        HashMap<Integer, Automobile> emptyMap = new HashMap<>();
        Mockito.when(automobileDataStorage.getM_allAutomobiles()).thenReturn(emptyMap);

        //check if Map is returned with correct code
        assertEquals(new ResponseEntity<>(emptyMap, HttpStatus.OK), automobileController.readAllAutomobile());
    }

    @Test
    void updateAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(AutomobileController.incorrectParameterResponse(), automobileController.updateAutomobile(new Automobile(), 99));

        //check Response code
        setMockIdCheckToFalse();
        assertEquals(HttpStatus.OK, automobileController.updateAutomobile(new Automobile(), 99).getStatusCode());
    }

    @Test
    void createAutomobile() {
        assertEquals(HttpStatus.OK, automobileController.createAutomobile(new Automobile()).getStatusCode());
    }

    @Test
    void deleteAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(AutomobileController.incorrectParameterResponse(), automobileController.deleteAutomobile(99));

        //check Response code
        setMockIdCheckToFalse();
        assertEquals(HttpStatus.NO_CONTENT, automobileController.deleteAutomobile(99).getStatusCode());
    }

    @Test
    void resetAllAutomobiles() {
        assertEquals(HttpStatus.NO_CONTENT, automobileController.resetAllAutomobiles().getStatusCode());
    }

    void setMockIdCheckToFalse(){
        Mockito.when(automobileDataStorage.checkForInvalidID(Mockito.anyInt())).thenReturn(false);
    }
    void setMockIdCheckToTrue(){
        Mockito.when(automobileDataStorage.checkForInvalidID(Mockito.anyInt())).thenReturn(true);
    }
}