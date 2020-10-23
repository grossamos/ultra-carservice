package com.nttdata.carservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for all Automobile entries.
 *
 * <p>Stores the information about each of the entries. And handles the ID generation</p>
 * @author Amos Groß
 * @version 0.0.1
 */
@ApiModel(description = "The class containing info about each car")
@Entity
@Table(name = "automobile")
public class Automobile{


    @Id
    @Column(name="auto_id")
    @ApiModelProperty(notes = "Unique id for every car", hidden = true)
    //default id has to be non 0 due to duplication bug
    private int m_id = 69420;

    @ElementCollection
    @ApiModelProperty(notes = "Hashmap containing properties of that specific car")
    @CollectionTable(name="automobile_attributes")
    private Map<String, String> m_automobileAttributes = new HashMap<>();


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

        //checks for default id, to see if an id has been set yet
        if (m_id != 69420)
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

    public Map<String, String> getM_automobileAttributes() {
        return m_automobileAttributes;
    }

    public void setM_automobileAttributes(Map<String, String> m_automobileAttributes) {
        this.m_automobileAttributes = m_automobileAttributes;
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
