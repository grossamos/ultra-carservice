package com.nttdata.carservice.controller;

import com.nttdata.carservice.entity.Automobile;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class AutomobileControllerTest {

    static AutomobileController automobileController;
    static AutomobileDataStorage automobileDataStorage = Mockito.mock(AutomobileDataStorage.class);
    static Automobile emptyAutomobile = Mockito.mock(Automobile.class);

    @BeforeAll
    static void setAutomobileController(){
        //initialize Controller with Mock Database
        automobileController = new AutomobileController(automobileDataStorage, null);

        //disable Logger
        Logger.getRootLogger().removeAllAppenders();
    }

    @Test
    void readSingleAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.BAD_REQUEST, automobileController.readSingleAutomobile(0).getStatusCode());

        //setup Mockito checkForInvalidID to say every ID is valid, and return empty Automobile at getAutomobileFromID
        setMockIdCheckToFalse();
        Mockito.when(automobileDataStorage.getAutomobileFromID(Mockito.anyInt())).thenReturn(emptyAutomobile);

        //test if Controller returns empty Automobile as Response code, with correct code
        assertEquals(HttpStatus.OK, automobileController.readSingleAutomobile(0).getStatusCode());
    }

    @Test
    void readAllAutomobile() {
        //setup mockito to return mock Hashmap
        HashMap<Integer, Automobile> emptyMap = Mockito.mock(HashMap.class);
        Mockito.when(automobileDataStorage.getM_allAutomobiles()).thenReturn(emptyMap);

        //check if Map is returned with correct code
        assertEquals(HttpStatus.OK, automobileController.readAllAutomobile().getStatusCode());
    }

    @Test
    void updateAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.BAD_REQUEST, automobileController.updateAutomobile(emptyAutomobile, 99).getStatusCode());

        //check Response code
        setMockIdCheckToFalse();
        assertEquals(HttpStatus.OK, automobileController.updateAutomobile(emptyAutomobile, 99).getStatusCode());
    }

    @Test
    void createAutomobile() {
        assertEquals(HttpStatus.OK, automobileController.createAutomobile(emptyAutomobile).getStatusCode());
    }

    @Test
    void deleteAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.BAD_REQUEST, automobileController.deleteAutomobile(99).getStatusCode());

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