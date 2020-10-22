package com.nttdata.carservice;

import com.nttdata.carservice.entity.Automobile;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileDataStorageTest {

    private static HashMap<Integer, Automobile> storageMap;
    private static final File storageFile = new File("./src/test/data/automobiles.json");;
    private static AutomobileDataStorage automobileDataStorage;

    @Nested
    class automobileFileHandling{

        private final File file = new File("./src/test/data/invalidFilePath.json");
        private FileWriter fileWriter;

        @BeforeEach
        void setFileWriter(){
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
        }

        @AfterEach
        void cleanUpFile() throws IOException {
            fileWriter.close();
            assertTrue(file.delete());
        }

        @Test
        void getAutomobilesFromFile() throws IOException {
            //create file with test contents
            assertNotNull(fileWriter);
            fileWriter.write("{\"666\":{\"m_id\":666,\"m_automobileAttributes\":{\"name\":\"ULTRA\"}}}");
            AutomobileDataStorage automobileDataStorage = new AutomobileDataStorage(new HashMap<>(), file);
            fileWriter.close();

            //get data from file and check if id is correct
            automobileDataStorage.getAutomobilesFromFile();
            assertEquals(automobileDataStorage.getAutomobileFromID(666).getM_name(), "ULTRA");
        }

        @Test
        void pushAutomobilesToFile() throws IOException {
            //create testClass
            AutomobileDataStorage emptyAutomobileDataStorage = new AutomobileDataStorage(new HashMap<>(), file);

            //check if new file is created
            fileWriter.close();
            assertTrue(file.delete());
            assertFalse(file.exists());
            emptyAutomobileDataStorage.pushAutomobilesToFile();
            assertTrue(file.exists());

            //add data to hashmap and check if file contains something
            emptyAutomobileDataStorage.addAutomobile(new Automobile());
            assertNotEquals(0, file.length());
        }
    }

    @BeforeEach
    void initStorage(){
        storageMap = new HashMap<>();
        automobileDataStorage = new AutomobileDataStorage(storageMap, storageFile);
    }

    @AfterEach
    void cleanStorage(){
        if (storageFile.exists())
            assertTrue(storageFile.delete());
    }

    @Test
    void addAutomobile() {
        //check if it allways adds an object to the HashMap, then checks, if an ID was added
        for (int i = 0; i < 200; i++) {
            int old_size = storageMap.size();
            Automobile autoToAdd = new Automobile();
            automobileDataStorage.addAutomobile(autoToAdd);
            //checks for valid add
            assertEquals(old_size + 1, storageMap.size());
            //checks if ID was set of new Object
            assertNotEquals(0, autoToAdd.getM_id());
        }
    }

    @Test
    void removeAutomobile() {

        ArrayList<Integer> allIDs = new ArrayList<>();
        for (int i = 0; i < 66; i++) {
            Automobile carToAdd = new Automobile();
            carToAdd.setValue("name", String.valueOf(i ^ 767));
            carToAdd.generateId(automobileDataStorage);
            storageMap.put(carToAdd.getM_id(), carToAdd);
            allIDs.add(carToAdd.getM_id());
        }

        //check if all cars were removed
        for (int id : allIDs) {
            automobileDataStorage.removeAutomobile(id);
        }
        assertEquals(0, storageMap.size());
    }

    @Test
    void changeAutomobile() {
        //add a car to map (with two values ~ one to change and one not to change)
        Automobile autoToUpdate = new Automobile();
        autoToUpdate.setValue("should be unchanged", "No change");
        autoToUpdate.setValue("should be changed0099999", "wat");
        automobileDataStorage.addAutomobile(autoToUpdate);

        //updates car
        Automobile autoWithUpdate = new Automobile();
        autoWithUpdate.setValue("should be changed0099999", "Some change");
        automobileDataStorage.changeAutomobile(autoToUpdate.getM_id(), autoWithUpdate);

        //check for correctness
        assertEquals("No change", storageMap.get(autoToUpdate.getM_id()).getValue("should be unchanged"));
        assertEquals("Some change", storageMap.get(autoToUpdate.getM_id()).getValue("should be changed0099999"));
    }

    @Test
    void checkForInvalidID() {
        //try to add a shit ton of items, then check if id is taken (which it should be)
        for (int i = 0; i < 420; i++) {
            Automobile automobileToAdd = new Automobile();
            automobileDataStorage.addAutomobile(automobileToAdd);
            assertFalse(automobileDataStorage.checkForInvalidID(automobileToAdd.getM_id()));
        }

    }

    @Test
    void clearM_allAutomobiles() {
        //add dummy data
        for (int i = 0; i < 69; i++) {
            automobileDataStorage.addAutomobile(new Automobile());
        }

        //remove dummy data and check if it's still there
        automobileDataStorage.clearM_allAutomobiles();
        assertEquals(0, storageMap.size());
    }

}