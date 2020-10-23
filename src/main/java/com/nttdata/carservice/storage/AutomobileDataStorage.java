package com.nttdata.carservice.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nttdata.carservice.entity.Automobile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * DataStorage class for Automobile entries.
 *
 * Provides an interface to interact with and store Automobile Objects.
 *
 * @author "Amos Gross"
 * @version 0.0.1
 */

public class AutomobileDataStorage {
    private HashMap<Integer, Automobile> m_allAutomobiles;
    private final File m_automobilesFile;
    static private final Gson m_gson = new Gson();
    private final AutomobileRepo m_automobileRepo;


    /**
     * Constructor for AutomobileDataStorage Dependency Injection.
     *
     * Injects dependency of storage class (hashmap) and of where to save the file later on.
     * @param automobileRepo Dependency Injected Database Object
     */

    public AutomobileDataStorage(AutomobileRepo automobileRepo){
        this.m_allAutomobiles = new HashMap<>();
        this.m_automobilesFile = new File("./src/main/resources/static/automobiles.json");
        this.m_automobileRepo = automobileRepo;
    }

    /**
     * Retrieves saved state from the file.
     */

    public void getAutomobilesFromFile(){
        try {
            m_automobilesFile.exists();
            Scanner scanner = new Scanner(m_automobilesFile);
            StringBuilder automobilesAsJSON = new StringBuilder();
            while (scanner.hasNextLine())
                automobilesAsJSON.append(scanner.nextLine()).append("\n");
            m_allAutomobiles = m_gson.fromJson(automobilesAsJSON.toString(), new TypeToken<HashMap<Integer, Automobile>>(){}.getType());
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current state to the file.
     */

    public void pushAutomobilesToFile(){
        try {
            m_automobilesFile.createNewFile();
            FileWriter jsonWriter = new FileWriter(m_automobilesFile.getPath());
            jsonWriter.write(m_gson.toJson(m_allAutomobiles));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an Automobile to storage.
     *
     * Adds it to the Hashmap and generates a new unique id. <u>Only to be used for initial adding</u>
     * @param someAutomobile Automobile entry to add
     */

    public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId(this);
        m_automobileRepo.save(someAutomobile);
    }

    /**
     * Removes an Automobile from storage
     *
     * @param id Id of Automobile to remove
     */

    public void removeAutomobile(int id){
        m_automobileRepo.delete(getAutomobileFromID(id));
    }

    /**
     * Updater for entries.
     *
     * @param id ID of object, that needs to be updated
     * @param changesAutomobile Object containing changes
     */

    public void changeAutomobile(int id, Automobile changesAutomobile){
        Automobile oldAutomobile = m_automobileRepo.findById(id).get();
        for (String key: changesAutomobile.getM_automobileAttributes().keySet()){
            oldAutomobile.setValue(key, changesAutomobile.getValue(key));
        }
        m_automobileRepo.save(changesAutomobile);
        //default id has to be non 0 due to some fucking reason
        m_automobileRepo.deleteById(69420);
    }

    /**
     * Checks if you would get a valid Object if requesting ID.
     *
     * @param id ID to check
     * @return bool if ID is Invalid or not
     */

    public boolean checkForInvalidID(int id){
        return m_automobileRepo.findById(id).isEmpty();
    }

    /**
     * Empties out the storage.
     *
     * Deletes all Entries that are in storage.
     */

    public void clearM_allAutomobiles(){
        m_automobileRepo.deleteAll();
    }

    public ArrayList<Automobile> getM_allAutomobiles() {
        ArrayList<Automobile> allAutomobiles = new ArrayList<>();
        for (Automobile automobile : m_automobileRepo.findAll()) {
            allAutomobiles.add(automobile);
        }
        return allAutomobiles;
    }

    public Automobile getAutomobileFromID(int id){
        return m_automobileRepo.findById(id).get();
    }
}
