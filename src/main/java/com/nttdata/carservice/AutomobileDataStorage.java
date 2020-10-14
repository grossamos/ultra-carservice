package com.nttdata.carservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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

    /**
     * Constructor for AutomobileDataStorage Dependency Injection.
     *
     * Injects dependency of storage class (hashmap) and of where to save the file later on.
     * @param allAutomobiles Injected storage
     * @param fileToSave Injected file save location
     */

    public AutomobileDataStorage(HashMap<Integer, Automobile> allAutomobiles, File fileToSave){
        this.m_allAutomobiles = allAutomobiles;
        this.m_automobilesFile = fileToSave;
    }

    /**
     * Retrieves saved state from the file.
     */

    public void getAutomobilesFromFile(){
        try {
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
            //noinspection ResultOfMethodCallIgnored
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
     * @param someAutomobile
     */

    public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId(this);
        m_allAutomobiles.put(someAutomobile.getM_id(), someAutomobile);
        pushAutomobilesToFile();
    }

    /**
     * Removes an Automobile from storage
     *
     * @param id Id of Automobile to remove
     */

    public void removeAutomobile(int id){
        m_allAutomobiles.remove(id);
        pushAutomobilesToFile();
    }

    /**
     * Updater for entries.
     *
     * @param id
     * @param changesAutomobile
     */

    public void changeAutomobile(int id, Automobile changesAutomobile){
        Automobile oldAutomobile = m_allAutomobiles.get(id);
        for (String key: changesAutomobile.getM_automobileAttributes().keySet()){
            oldAutomobile.setValue(key, changesAutomobile.getValue(key));
        }
    }

    /**
     * Checks if you would get a valid Object if requesting ID.
     *
     * @param id ID to check
     */

    public boolean checkForInvalidID(int id){
        return m_allAutomobiles.get(id) == null;
    }

    /**
     * Empties out the storage.
     *
     * Deletes all Entries that are in storage.
     */

    public void clearM_allAutomobiles(){
        m_allAutomobiles.clear();
        pushAutomobilesToFile();
    }

    public HashMap<Integer, Automobile> getM_allAutomobiles() {
        return m_allAutomobiles;
    }

    public Automobile getAutomobileFromID(int id){
        return m_allAutomobiles.get(id);
    }
}
