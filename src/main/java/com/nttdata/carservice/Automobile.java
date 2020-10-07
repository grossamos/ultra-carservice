package com.nttdata.carservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Automobile{

    @JsonIgnore
    public static int m_maxIndex = 0;

    private int m_id = 0;
    private String m_name;
    private String m_model;


    public Automobile(){
    }

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
        if (m_id != 0)
            return;

        int new_id;
        int id_size = 10000;

        if (m_name == null && m_model == null)
            new_id = 1;
        else if (m_name == null)
            new_id = m_model.hashCode() % id_size;
        else if (m_model == null)
            new_id = m_name.hashCode() % id_size;
        else
            new_id = (m_name.hashCode() ^ m_model.hashCode()) % 10000;

        while (!AutomobileDataStorage.checkForInvalidID(new_id)){
            new_id++;
        }
        this.m_id = new_id;

    }

    public static Automobile jsonToAutomobile(String json){
        // Has to be updated with new parameters
        String newName = jsonKeyToValue("name", json);
        String newModel = jsonKeyToValue("model", json);

        if (newName == null && newModel == null)
            return new Automobile();
        else if (newName == null)
            return new Automobile(null, newModel);
        else if (newModel == null)
            return new Automobile(newName, null);
        else
            return new Automobile(newName, newModel);
    }

    public static String jsonKeyToValue(String key, String json){
        if (!json.contains(key))
            return null;

        int startIndex = json.indexOf(key) + key.length() + 3;
        json = json.substring(startIndex);

        int endIndex = json.indexOf('"');
        json = json.substring(0, endIndex);
        return json;
    }

    public static Automobile updateAutomobileWithTemplate(Automobile oldAutomobile, Automobile templateAutomobile){
        //has to be updated with new parameters
        if (oldAutomobile == null && templateAutomobile == null){
            return new Automobile();
        }
        else if (templateAutomobile.m_name != null && templateAutomobile.m_model != null){
            oldAutomobile.setM_name(templateAutomobile.getM_name());
            oldAutomobile.setM_model(templateAutomobile.getM_model());
        }
        else if (templateAutomobile.m_name != null){
            oldAutomobile.setM_name(templateAutomobile.getM_name());
        }
        else if (templateAutomobile.m_model != null){
            oldAutomobile.setM_model(templateAutomobile.getM_model());
        }
        return oldAutomobile;
    }

    public String getM_model() {
        return m_model;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public void setM_model(String m_model) {
        this.m_model = m_model;
    }

}
