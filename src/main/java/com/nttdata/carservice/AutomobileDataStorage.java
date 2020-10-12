package com.nttdata.carservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
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
            m_automobilesFile.createNewFile();
            Scanner scanner = new Scanner(m_automobilesFile);
            StringBuilder automobilesAsJSON = new StringBuilder();
            while (scanner.hasNextLine())
                automobilesAsJSON.append(scanner.nextLine()).append("\n");

            HashMap<Integer, Automobile> automobileArrayList = m_gson.fromJson(automobilesAsJSON.toString(), new TypeToken<HashMap<Integer, Automobile>>(){}.getType());
            Automobile.m_maxIndex = automobileArrayList.size();
            m_allAutomobiles = automobileArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pushAutomobilesToFile(){
        try {
            FileWriter jsonWriter = new FileWriter(m_automobilesFile.getPath());
            jsonWriter.write(m_gson.toJson(m_allAutomobiles));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId(this);
        Automobile.m_maxIndex++;
        m_allAutomobiles.put(someAutomobile.getM_id(), someAutomobile);
        pushAutomobilesToFile();
    }

    public void removeAutomobile(int id){
        m_allAutomobiles.remove(id);
        Automobile.m_maxIndex--;
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

    public void clearM_allAutomobiles(){
        m_allAutomobiles = new HashMap<>();
        pushAutomobilesToFile();
    }
}
