package com.nttdata.carservice.controller;

import com.nttdata.carservice.Automobile;
import com.nttdata.carservice.AutomobileDataStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class AutomobileControllerTest {

    static AutomobileController automobileController;
    static AutomobileDataStorage automobileDataStorage;
    private static ArrayList<Automobile> mockArray;

    @BeforeAll
    static void setAutomobileController(){
        mockArray = new ArrayList<>();
        mockArray.add(new Automobile());
        mockArray.get(0).setM_name("Bob");

        automobileDataStorage = Mockito.mock(AutomobileDataStorage.class);
        Mockito.when(automobileDataStorage.getM_allAutomobiles()).thenReturn(mockArray);
//        Mockito.when(automobileDataStorage.getAutomobileFromID
        automobileController = new AutomobileController(automobileDataStorage);
    }

    @Test
    void readAllAutomobile() {
        ResponseEntity<?> responseEntity = new ResponseEntity<>(mockArray, HttpStatus.OK);
        assertEquals(new ResponseEntity<>(automobileDataStorage.getM_allAutomobiles(), HttpStatus.OK), responseEntity);
    }


    @Test
    void readSingleAutomobile() {
        for (int id : getTrueIdsList()){
            assertEquals(new ResponseEntity<>(automobileDataStorage.getAutomobileFromID(id), HttpStatus.OK), automobileController.readSingleAutomobile(id));
        }
        for (int id : getFalseIdsList(10)){
            assertEquals(AutomobileController.incorrectParameterResponse(), automobileController.readSingleAutomobile(id));
        }
    }

    @Test
    void updateAutomobile() {
    }

    @Test
    void deleteAutomobile() {
    }

    @Test
    void incorrectParameterResponse() {
    }

    @Test
    void resetAllAutomobiles() {
    }

    ArrayList<Integer> getTrueIdsList(){
        ArrayList<Integer> allIds = new ArrayList<>();
        for (Automobile automobile: automobileDataStorage.getM_allAutomobiles()) {
            allIds.add(automobile.getM_id());
        }
        return allIds;
    }
    ArrayList<Integer> getFalseIdsList(int amountOfIds){
        ArrayList<Integer> allIds = new ArrayList<>();
        int counter = 0;
        int possibleId = 0;
        while (counter < amountOfIds){
            if (automobileDataStorage.checkForInvalidID(possibleId)) {
                allIds.add(possibleId);
                counter++;
            }
            possibleId++;
        }
        return allIds;
    }
}