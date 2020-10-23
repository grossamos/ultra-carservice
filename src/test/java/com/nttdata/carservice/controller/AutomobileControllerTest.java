package com.nttdata.carservice.controller;

import com.nttdata.carservice.entity.Automobile;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import com.nttdata.carservice.storage.AutomobileRepo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class AutomobileControllerTest {

    static Automobile emptyAutomobile = Mockito.mock(Automobile.class);
    static Map<String, String> emptyHashmap = Mockito.mock(HashMap.class);
    static AutomobileRepo automobileRepo = Mockito.mock(AutomobileRepo.class);
    static ArrayList<Automobile> mockArrayList = Mockito.mock(ArrayList.class);
    static AutomobileDataStorage automobileDataStorage = Mockito.mock(AutomobileDataStorage.class);
    static AutomobileController automobileController = new AutomobileController(automobileRepo, automobileDataStorage);

    @BeforeAll
    static void setAutomobileController(){
        //disable logger
        Logger.getRootLogger().removeAllAppenders();

        //setup mockito objects
        Mockito.when(automobileDataStorage.getM_allAutomobiles()).thenReturn(mockArrayList);
        Mockito.when(emptyAutomobile.getM_automobileAttributes()).thenReturn(emptyHashmap);
        Mockito.when(emptyHashmap.isEmpty()).thenReturn(false);
    }

    @Test
    void readSingleAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.NOT_FOUND, automobileController.readSingleAutomobile(0).getStatusCode());

        //setup Mockito checkForInvalidID to say every ID is valid, and return empty Automobile at getAutomobileFromID
        setMockIdCheckToFalse();
        Mockito.when(automobileDataStorage.getAutomobileFromID(Mockito.anyInt())).thenReturn(emptyAutomobile);

        //test if Controller returns empty Automobile as Response code, with correct code
        assertEquals(HttpStatus.OK, automobileController.readSingleAutomobile(0).getStatusCode());
    }

    @Test
    void readAllAutomobile_with_empty_map() {
        //setup mockito to return mock Hashmap
        setIsEmptyCheckTo(true);

        //check if Map is returned with correct code
        assertEquals(HttpStatus.NO_CONTENT, automobileController.readAllAutomobile().getStatusCode());
    }

    @Test
    void readAllAutomobile_with_non_empty_map() {
        //setup mockito to return mock Hashmap
        setIsEmptyCheckTo(false);

        //check if Map is returned with correct code
        assertEquals(HttpStatus.OK, automobileController.readAllAutomobile().getStatusCode());
    }

    @Test
    void updateAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.NOT_FOUND, automobileController.updateAutomobile(emptyAutomobile, 99).getStatusCode());

        //check Response code
        setMockIdCheckToFalse();
        setIsEmptyCheckTo(false);
        assertEquals(HttpStatus.OK, automobileController.updateAutomobile(emptyAutomobile, 99).getStatusCode());
    }

    @Test
    void check_response_code_of_createAutomobile() {
        assertEquals(HttpStatus.CREATED, automobileController.createAutomobile(emptyAutomobile).getStatusCode());
    }

    @Test
    void deleteAutomobile() {
        //check ID validity checker
        setMockIdCheckToTrue();
        assertEquals(HttpStatus.NOT_FOUND, automobileController.deleteAutomobile(99).getStatusCode());

        //check Response code
        setMockIdCheckToFalse();
        assertEquals(HttpStatus.NO_CONTENT, automobileController.deleteAutomobile(99).getStatusCode());
    }

    @Test
    void resetAllAutomobiles() {
        assertEquals(HttpStatus.NO_CONTENT, automobileController.resetAllAutomobiles().getStatusCode());
    }

    void setIsEmptyCheckTo(boolean returnBool){
        Mockito.when(mockArrayList.isEmpty()).thenReturn(returnBool);
    }

    void setMockIdCheckToFalse(){
        Mockito.when(automobileDataStorage.checkForInvalidID(Mockito.anyInt())).thenReturn(false);
    }
    void setMockIdCheckToTrue(){
        Mockito.when(automobileDataStorage.checkForInvalidID(Mockito.anyInt())).thenReturn(true);
    }
}