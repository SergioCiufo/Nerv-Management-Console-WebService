package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SimulationTest {
	private Simulation simulation = new Simulation();
	
	@Test
    void shouldCreateSimulationWithCorrectValues_whenValidParametersProvided() {
        //parameters
        Integer exp = 100;
        Integer level = 5;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 70;
        Integer supportAbility = 60;
        String name = "Simulation A";
        Integer durationTime = 120;

        //mocks
        List<SimulationParticipant> participantsMock = mock(List.class);

        //test
        Simulation simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        simulation.setSimulationParticipants(participantsMock);

        //results
        assertNotNull(simulation);
        assertEquals(exp, simulation.getExp());
        assertEquals(level, simulation.getLevel());
        assertEquals(synchronizationRate, simulation.getSynchronizationRate());
        assertEquals(tacticalAbility, simulation.getTacticalAbility());
        assertEquals(supportAbility, simulation.getSupportAbility());
        assertEquals(name, simulation.getName());
        assertEquals(durationTime, simulation.getDurationTime());
        assertEquals(participantsMock, simulation.getSimulationParticipants());
    }

    @Test
    void shouldCalculateHashCodeCorrectly_whenSimulationIsCreated() {
        //parameters
        Integer exp = 100;
        Integer level = 5;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 70;
        Integer supportAbility = 60;
        String name = "Simulation A";
        Integer durationTime = 120;

        //mocks
        List<SimulationParticipant> participantsMock = mock(List.class);

        //test
        Simulation simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        simulation.setSimulationParticipants(participantsMock);
        Simulation anotherSimulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        anotherSimulation.setSimulationParticipants(participantsMock);

        //results
        assertEquals(simulation.hashCode(), anotherSimulation.hashCode());
    }

    @Test
    void shouldReturnTrue_whenSimulationObjectsAreEqual() {
        //parameters
        Integer exp = 100;
        Integer level = 5;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 70;
        Integer supportAbility = 60;
        String name = "Simulation A";
        Integer durationTime = 120;

        //mocks
        List<SimulationParticipant> participantsMock = mock(List.class);

        //test
        Simulation simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        simulation.setSimulationParticipants(participantsMock);
        Simulation anotherSimulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        anotherSimulation.setSimulationParticipants(participantsMock);

        //results
        assertTrue(simulation.equals(anotherSimulation));
    }

    @Test
    void shouldReturnCorrectString_whenToStringIsCalled() {
        //parameters
        Integer exp = 100;
        Integer level = 5;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 70;
        Integer supportAbility = 60;
        String name = "Simulation A";
        Integer durationTime = 120;

        //mocks
        List<SimulationParticipant> participantsMock = mock(List.class);

        //test
        Simulation simulation = new Simulation(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        simulation.setSimulationParticipants(participantsMock);

        //results
        String expectedString = "Simulation [getName()=" + name + ", getDurationTime()=" + durationTime + ", toString="
                + simulation.getClass().getSuperclass().getName() + ", getExp()=" + exp + ", getLevel()="
                + level + ", getSynchronizationRate()=" + synchronizationRate + ", getTacticalAbility()="
                + tacticalAbility + ", getSupportAbility()=" + supportAbility + "]";
        assertTrue(simulation.toString().contains(name));
    }
}
