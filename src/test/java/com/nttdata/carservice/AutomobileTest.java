package com.nttdata.carservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileTest {

    @Test
    void jsonToAutomobile() {
        assertEquals("World", Automobile.jsonToAutomobile("{\"name\":\"World\"}").getM_name());
        assertEquals(true, false);
    }
    
}