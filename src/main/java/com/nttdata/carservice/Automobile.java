package com.nttdata.carservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Automobile{

    @JsonIgnore
    public static int m_maxIndex = 0;

    private int m_id;
    private int m_index;
    private final String m_name;
    private final String m_model;

    public Automobile(String name, String model) {
        // ID has to be generated after Index is known!
        this.m_name = name;
        this.m_model = model;
    }

    public String getM_name() {
        return m_name;
    }

    public int getM_id() {
        return m_id;
    }

    public void generateId(){
        int new_id = (m_name.hashCode() ^ m_model.hashCode()) % 10000;
        while (!AutomobileDataStorage.checkForInvalidID(new_id)){
            new_id++;
        }
        this.m_id = new_id;
    }

    public String getM_model() {
        return m_model;
    }

    public int getM_index() {
        return m_index;
    }

    public void setM_index(int m_index) {
        this.m_index = m_index;
        generateId();
    }
}
