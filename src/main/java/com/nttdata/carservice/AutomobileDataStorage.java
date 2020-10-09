package com.nttdata.carservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class AutomobileDataStorage {
    private ArrayList<Automobile> m_allAutomobiles = new ArrayList<>();
    private final File m_automobilesFile;
    static private final Gson m_gson = new Gson();


    public AutomobileDataStorage(ArrayList<Automobile> allAutomobiles, String pathToSave){
        this.m_allAutomobiles = allAutomobiles;
        this.m_automobilesFile = new File(pathToSave);
    }

    public void getAutomobilesFromFile(){
        try {

            Scanner scanner = new Scanner(m_automobilesFile);
            StringBuilder automobilesAsJSON = new StringBuilder();
            while (scanner.hasNextLine())
                automobilesAsJSON.append(scanner.nextLine()).append("\n");

            ArrayList<Automobile> automobileArrayList = m_gson.fromJson(automobilesAsJSON.toString(), new TypeToken<ArrayList<Automobile>>(){}.getType());
            Automobile.m_maxIndex = automobileArrayList.size();
            m_allAutomobiles = automobileArrayList;

        } catch (FileNotFoundException e) {
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

    public Automobile getAutomobileFromIndex(int index){
        return m_allAutomobiles.get(index);
    }

    public  Automobile getAutomobileFromID(int id){
        for (Automobile potentialFind : m_allAutomobiles){
            if (potentialFind.getM_id() == id) {
                return potentialFind;
            }
        }
        return null;
    }

    public int getIndexFromId(int id){
        int counter = 0;
        for (Automobile curAutomobile: m_allAutomobiles){
            if (id == curAutomobile.getM_id()){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId(this);
        Automobile.m_maxIndex++;
        m_allAutomobiles.add(someAutomobile);
        pushAutomobilesToFile();
    }

    public void removeAutomobile(int index){
        m_allAutomobiles.remove(index);
        Automobile.m_maxIndex--;
        pushAutomobilesToFile();
    }

    public void changeAutomobile(int id, Automobile changesAutomobile){
        Automobile oldAutomobile = getAutomobileFromID(id);
        Automobile newAutomobile = Automobile.updateAutomobileWithTemplate(oldAutomobile, changesAutomobile);
        removeAutomobile(getIndexFromId(id));
        addAutomobile(newAutomobile);
        pushAutomobilesToFile();
    }

    public boolean checkForInvalidID(int id){
        return getAutomobileFromID(id) == null;
    }

    public ArrayList<Automobile> getM_allAutomobiles() {
        return m_allAutomobiles;
    }

    public void clearM_allAutomobiles(){
        m_allAutomobiles = new ArrayList<>();
        pushAutomobilesToFile();
    }
}
