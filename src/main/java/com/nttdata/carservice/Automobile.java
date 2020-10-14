package com.nttdata.carservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/**
 * A class for all Automobile entries.
 *
 * <p>Stores the information about each of the entries. And handles the ID generation</p>
 * @author Amos Groß
 * @version 0.0.1
 */

@ApiModel(description = "The class containing info about each car")
public class Automobile{

    @ApiModelProperty(notes = "Unique id for every car", hidden = true)
    private int m_id = 0;

    @ApiModelProperty(notes = "Hashmap containing properties of that specific car")
    private final HashMap<String, String> m_automobileAttributes = new HashMap<>();


    public int getM_id() {
        return m_id;
    }

    /**
     * Generates an Automobiles ID.
     *
     * Creates a hash from <b>name</b> and <b>model</b> fields in Hashmap (if present) and checks if it's unique.
     * If not we just add 1 until it is.
     * @param automobileDataStorage Storage Class in which the ID needs to be unique
     */

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

    @JsonIgnore
    public String getM_model() {
        return m_automobileAttributes.get("model");
    }
}
