package com.company.NervManagementConsoleREST.model;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MissionParticipantsTest {

    @Test
    public void shouldCreateMissionParticipants_whenConstructorIsCalled() {
        // parameters
        Mission mission = mock(Mission.class); 
        User user = mock(User.class);  
        Member member = mock(Member.class); 

        // test
        MissionParticipants missionParticipants = new MissionParticipants(mission, user, member);

        // result
        assertEquals(mission, missionParticipants.getMission());
        assertEquals(user, missionParticipants.getUser());
        assertEquals(member, missionParticipants.getMember());
    }

    @Test
    public void shouldSetValuesCorrectly_whenSettersAreCalled() {
        // parameters
        MissionParticipants missionParticipants = new MissionParticipants();
        Mission mission = mock(Mission.class); 
        User user = mock(User.class);        
        Member member = mock(Member.class);   

        // test
        missionParticipants.setMission(mission);
        missionParticipants.setUser(user);
        missionParticipants.setMember(member);

        // result
        assertEquals(mission, missionParticipants.getMission());
        assertEquals(user, missionParticipants.getUser());
        assertEquals(member, missionParticipants.getMember());
    }

    @Test
    public void shouldReturnTrue_whenEqualsIsCalledForEqualObjects() {
        // parameters
        MissionParticipants mp1 = new MissionParticipants();
        mp1.setMissionParticipantsId(1);

        MissionParticipants mp2 = new MissionParticipants();
        mp2.setMissionParticipantsId(1);

        // test
        boolean isEqual = mp1.equals(mp2);

        // result
        assertTrue(isEqual);
    }

    @Test
    public void shouldReturnFalse_whenEqualsIsCalledForDifferentObjects() {
        // parameters
        MissionParticipants mp1 = new MissionParticipants();
        mp1.setMissionParticipantsId(1);

        MissionParticipants mp2 = new MissionParticipants();
        mp2.setMissionParticipantsId(2);

        // test
        boolean isEqual = mp1.equals(mp2);

        // result
        assertFalse(isEqual);
    }

    @Test
    public void shouldReturnHashCode_whenHashCodeIsCalled() {
        // parameters
        MissionParticipants mp1 = new MissionParticipants();
        mp1.setMissionParticipantsId(1);

        MissionParticipants mp2 = new MissionParticipants();
        mp2.setMissionParticipantsId(1);

        // test
        assertEquals(mp1.hashCode(), mp2.hashCode());
    }

    @Test
    public void shouldReturnToString_whenToStringIsCalled() {
        // parameters
        Mission mission = mock(Mission.class);
        MissionParticipants mp = new MissionParticipants();
        mp.setMissionParticipantsId(1);
        mp.setMission(mission);

        // test
        String expected = "MissionParticipants [missionParticipantsId=1, mission=" + mission + "]";
        assertEquals(expected, mp.toString());
    }
}