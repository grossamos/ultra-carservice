package com.nttdata.carservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class AutomobileDataStorage {
    private HashMap<Integer, Automobile> m_allAutomobiles;
    private final File m_automobilesFile;
    static private final Gson m_gson = new Gson();


    public AutomobileDataStorage(HashMap<Integer, Automobile> allAutomobiles, File fileToSave){
        this.m_allAutomobiles = allAutomobiles;
        this.m_automobilesFile = fileToSave;
    }

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

    public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId(this);
        m_allAutomobiles.put(someAutomobile.getM_id(), someAutomobile);
        pushAutomobilesToFile();
    }

    public void removeAutomobile(int id){
        m_allAutomobiles.remove(id);
        pushAutomobilesToFile();
    }

    public void changeAutomobile(int id, Automobile changesAutomobile){
        Automobile oldAutomobile = m_allAutomobiles.get(id);
        for (String key: changesAutomobile.getM_automobileAttributes().keySet()){
            oldAutomobile.setValue(key, changesAutomobile.getValue(key));
        }
    }

    public boolean checkForInvalidID(int id){
        return m_allAutomobiles.get(id) == null;
    }

    public HashMap<Integer, Automobile> getM_allAutomobiles() {
        return m_allAutomobiles;
    }

    public Automobile getAutomobileFromID(int id){
        return m_allAutomobiles.get(id);
    }

    public void clearM_allAutomobiles(){
        m_allAutomobiles.clear();
        pushAutomobilesToFile();
    }
}
