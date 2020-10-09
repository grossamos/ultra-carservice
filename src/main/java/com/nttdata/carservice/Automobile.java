package com.nttdata.carservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class Automobile{
    //TODO replace fields with ArrayList or hashMap ("names" as keys)
    // 1. implement changes in jsonToAutomobile() function ~ still take same array, but set

    @JsonIgnore
    public static int m_maxIndex = 0;

    private int m_id = 0;
    private HashMap<String, String> m_automobileAttributes = new HashMap<>();


    public int getM_id() {
        return m_id;
    }

    public void generateId(AutomobileDataStorage automobileDataStorage){
        if (m_id != 0)
            return;

        int new_id;
        int id_size = 10000;

        if (this.getM_name() == null && this.getM_model() == null)
            new_id = 1;
        else if (this.getM_name() == null)
            new_id = this.getM_model().hashCode() % id_size;
        else if (this.getM_model() == null)
            new_id = this.getM_name().hashCode() % id_size;
        else
            new_id = (this.getM_name().hashCode() ^ this.getM_model().hashCode()) % 10000;

        while (!automobileDataStorage.checkForInvalidID(new_id)){
            new_id++;
        }
        this.m_id = new_id;

    }

    public void setValue(String key, String attribute){
        this.m_automobileAttributes.put(key, attribute);
    }

    public String getValue(String key){
        return this.m_automobileAttributes.get(key);
    }

    public HashMap<String, String> getM_automobileAttributes() {
        return m_automobileAttributes;
    }

    @JsonIgnore
    public String getM_name() {
        return m_automobileAttributes.get("name");
    }

    public void setM_name(String m_name) {
        this.m_automobileAttributes.put("name", m_name);
    }

    @JsonIgnore
    public String getM_model() {
        return m_automobileAttributes.get("model");
    }

    public void setM_model(String m_model) {
        this.m_automobileAttributes.put("name", m_model);
    }

}
