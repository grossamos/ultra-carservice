package com.nttdata.carservice;

import com.nttdata.carservice.automobile.Automobile;
import com.nttdata.carservice.automobile.AutomobileDataStorage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileTest {

    @Test
    void generateId() {

        //Setup mockito object
        HashMap<Integer, Boolean> mockIdList = new HashMap<>();
        Answer<Boolean> answer = invocationOnMock -> !mockIdList.containsKey(invocationOnMock.getArgument(0, Integer.class));
        AutomobileDataStorage automobileDataStorage = Mockito.mock(AutomobileDataStorage.class);
        Mockito.when(automobileDataStorage.checkForInvalidID(Mockito.anyInt())).thenAnswer(answer);

        //if nothing is given default to 1
        Automobile automobileEmpty = new Automobile();
        automobileEmpty.generateId(automobileDataStorage);
        mockIdList.put(automobileEmpty.getM_id(), true);
        assertEquals(1, automobileEmpty.getM_id());

        //try if two of the same contents (also empty) have the same id
        Automobile automobileAlsoEmpty = new Automobile();
        automobileAlsoEmpty.generateId(automobileDataStorage);
        assertFalse(mockIdList.containsKey(automobileAlsoEmpty.getM_id()));
        mockIdList.put(automobileAlsoEmpty.getM_id(), true);

        //try 200 new cars (with kinda random info) and check, if they overlap ids
        for (int i = 0; i < 200; i++) {
            Automobile automobileFull = new Automobile();
            automobileFull.setValue("name", String.valueOf(String.valueOf(i).hashCode() ^ 666));
            automobileFull.setValue("model", String.valueOf(String.valueOf(i).hashCode() ^ 999));
            automobileFull.generateId(automobileDataStorage);
            assertFalse(mockIdList.containsKey(automobileFull.getM_id()));
            mockIdList.put(automobileFull.getM_id(), true);
        }

    }

    @Test
    void setValue() {
        Automobile automobileEmpty = new Automobile();
        String testInput = "oMEGA Test";
        automobileEmpty.setValue("name", testInput);
        assertEquals(automobileEmpty.getM_name(), testInput);

        int cachedSize = automobileEmpty.getM_automobileAttributes().size();

        automobileEmpty.setValue("Some key", "Some Value");
        assertEquals(cachedSize + 1, automobileEmpty.getM_automobileAttributes().size());
    }



    @Test
    void getValue() {
        Automobile automobileEmpty = new Automobile();
        String testInput = "oMEGA Test";
        automobileEmpty.setValue("name", testInput);
        assertEquals(automobileEmpty.getValue("name"), testInput);
    }

}