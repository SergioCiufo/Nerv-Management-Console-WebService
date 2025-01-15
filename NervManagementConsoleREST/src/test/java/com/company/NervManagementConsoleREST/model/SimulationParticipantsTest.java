package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class SimulationParticipantsTest {
	private SimulationParticipant simulationParticipant = new SimulationParticipant();
	
	@Test
    void shouldCreateSimulationParticipantWithCorrectValues_whenValidParametersProvided() {
        // parameters
        Simulation simulationMock = mock(Simulation.class);
        User userMock = mock(User.class);
        Member memberMock = mock(Member.class);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);

        // test
        SimulationParticipant participant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);

        // results
        assertNotNull(participant);
        assertEquals(simulationMock, participant.getSimulation());
        assertEquals(userMock, participant.getUser());
        assertEquals(memberMock, participant.getMember());
        assertEquals(startTime, participant.getStartTime());
        assertEquals(endTime, participant.getEndTime());
    }

    @Test
    void shouldCalculateHashCodeCorrectly_whenSimulationParticipantIsCreated() {
        // parameters
        Simulation simulationMock = mock(Simulation.class);
        User userMock = mock(User.class);
        Member memberMock = mock(Member.class);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);

        // test
        SimulationParticipant participant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);
        SimulationParticipant anotherParticipant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);

        // results
        assertEquals(participant.hashCode(), anotherParticipant.hashCode());
    }

    @Test
    void shouldReturnTrue_whenSimulationParticipantObjectsAreEqual() {
        // parameters
        Simulation simulationMock = mock(Simulation.class);
        User userMock = mock(User.class);
        Member memberMock = mock(Member.class);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);

        // test
        SimulationParticipant participant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);
        SimulationParticipant anotherParticipant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);

        // results
        assertTrue(participant.equals(anotherParticipant));
    }

    @Test
    void shouldReturnCorrectString_whenToStringIsCalled() {
        // parameters
        Simulation simulationMock = mock(Simulation.class);
        User userMock = mock(User.class);
        Member memberMock = mock(Member.class);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);

        // test
        SimulationParticipant participant = new SimulationParticipant(simulationMock, userMock, memberMock, startTime, endTime);

        // results
        String expectedString = "SimulationParticipant [simulationParticipantId=null, startTime=" + startTime
                + ", endTime=" + endTime + "]";
        assertTrue(participant.toString().contains("startTime=" + startTime.toString()));
    }
}
