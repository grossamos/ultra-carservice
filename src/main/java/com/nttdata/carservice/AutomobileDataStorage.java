package com.nttdata.carservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nttdata.carservice.Automobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class AutomobileDataStorage {
    static private ArrayList<Automobile> m_allAutomobiles = new ArrayList<>();
    static private final File m_automobilesFile = new File("./src/data/automobiles.json");
    static private final Gson m_gson = new Gson();

    static public void getAutomobilesFromFile(){
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

    static public void pushAutomobilesToFile(){
        try {
            FileWriter jsonWriter = new FileWriter(m_automobilesFile.getPath());
            jsonWriter.write(m_gson.toJson(m_allAutomobiles));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Automobile getAutomobileFromIndex(int index){
        return m_allAutomobiles.get(index);
    }

    static public  Automobile getAutomobileFromID(int id){
        for (Automobile potentialFind : m_allAutomobiles){
            if (potentialFind.getM_id() == id) {
                return potentialFind;
            }
        }
        return null;
    }

    static public int getIndexFromId(int id){
        int counter = 0;
        for (Automobile curAutomobile: m_allAutomobiles){
            if (id == curAutomobile.getM_id()){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    static public void addAutomobile(Automobile someAutomobile){
        someAutomobile.generateId();
        Automobile.m_maxIndex++;
        m_allAutomobiles.add(someAutomobile);
        pushAutomobilesToFile();
    }

    static public void removeAutomobile(int index){
        m_allAutomobiles.remove(index);
        Automobile.m_maxIndex--;
        pushAutomobilesToFile();
    }

    static public void changeAutomobile(int id, Automobile changesAutomobile){
        Automobile oldAutomobile = getAutomobileFromID(id);
        Automobile newAutomobile = Automobile.updateAutomobileWithTemplate(oldAutomobile, changesAutomobile);
        removeAutomobile(getIndexFromId(id));
        addAutomobile(newAutomobile);
        pushAutomobilesToFile();
    }

    static public boolean checkForInvalidID(int id){
        return getAutomobileFromID(id) == null;
    }

    public static ArrayList<Automobile> getM_allAutomobiles() {
        return m_allAutomobiles;
    }

    public static void clearM_allAutomobiles(){
        m_allAutomobiles = new ArrayList<>();
        pushAutomobilesToFile();
    }
}
