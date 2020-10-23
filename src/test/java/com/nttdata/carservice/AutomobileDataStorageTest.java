package com.nttdata.carservice;

import com.nttdata.carservice.entity.Automobile;
import com.nttdata.carservice.storage.AutomobileDataStorage;
import com.nttdata.carservice.storage.AutomobileRepo;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileDataStorageTest {

    private static final AutomobileRepo automobileRepo = Mockito.mock(AutomobileRepo.class);
    private static final Automobile emptyAutomobile = Mockito.mock(Automobile.class);
    private static AutomobileDataStorage automobileDataStorage = new AutomobileDataStorage(automobileRepo);


    @BeforeEach
    void initStorage(){
    }

    @Test
    void check_if_addAutomobile_added_car_to_repo(){

        automobileDataStorage.addAutomobile(emptyAutomobile);
        Mockito.verify(automobileRepo, Mockito.times(1)).save(Mockito.any(Automobile.class));
    }

    @Test
    void check_if_removeAutomobile_removed_car_from_repo(){
        Mockito.when(automobileRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(emptyAutomobile));
        automobileDataStorage.removeAutomobile(420);
        Mockito.verify(automobileRepo, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    void check_if_changeAutomobile_() {
        //add a car to map (with two values ~ one to change and one not to change)
        Automobile autoToUpdate = new Automobile();
        autoToUpdate.setValue("should be unchanged", "No change");
        autoToUpdate.setValue("should be changed", "No change");
        Mockito.when(automobileRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(autoToUpdate));

        //updates car
        Automobile autoWithUpdate = new Automobile();
        autoWithUpdate.setValue("should be changed", "Some change");
        automobileDataStorage.changeAutomobile(autoToUpdate.getM_id(), autoWithUpdate);

        //check for correctness
        assertEquals("No change", autoToUpdate.getValue("should be unchanged"));
        assertEquals("Some change", autoWithUpdate.getValue("should be changed"));
    }

}