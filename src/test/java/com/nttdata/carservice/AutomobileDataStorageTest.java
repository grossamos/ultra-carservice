package com.nttdata.carservice;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileDataStorageTest {

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
            assertEquals(automobileDataStorage.getM_allAutomobiles().get(666).getM_name(), "ULTRA");
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



    @Test
    void addAutomobile() {
    }

    @Test
    void removeAutomobile() {
    }

    @Test
    void changeAutomobile() {
    }

    @Test
    void checkForInvalidID() {
    }

    @Test
    void clearM_allAutomobiles() {
    }

}