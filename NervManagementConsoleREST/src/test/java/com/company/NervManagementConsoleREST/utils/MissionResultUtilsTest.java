package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class MissionResultUtilsTest {
	private MissionResultUtils missionResultUtils = new MissionResultUtils();
	
	@Test
    public void shouldReturnResult_whenAllOk() {
        //parameters
        Mission mission = new Mission();
        mission.setMissionId(1);
        mission.setSynchronizationRate(70);
        mission.setSupportAbility(60);
        mission.setTacticalAbility(80);

        MissionParticipants participant1 = new MissionParticipants();
        MissionParticipants participant2 = new MissionParticipants();

        Member member1 = new Member();
        member1.setIdMember(101);

        Member member2 = new Member();
        member2.setIdMember(102);

        participant1.setMission(mission);
        participant1.setMember(member1);

        participant2.setMission(mission);
        participant2.setMember(member2);

        mission.setMissionParticipants(Arrays.asList(participant1, participant2));

        UserMembersStats stats1 = new UserMembersStats();
        stats1.setMember(member1);
        stats1.setSynchronizationRate(75);
        stats1.setTacticalAbility(85);
        stats1.setSupportAbility(65);

        UserMembersStats stats2 = new UserMembersStats();
        stats2.setMember(member2);
        stats2.setSynchronizationRate(80);
        stats2.setTacticalAbility(90);
        stats2.setSupportAbility(70);

        List<UserMembersStats> userMembersStats = Arrays.asList(stats1, stats2);

        CalculateUtils calculateUtils = mock(CalculateUtils.class);
        Boolean expectedResult = true;
        
        //mock
        doReturn(expectedResult).when(calculateUtils).calculateWinLoseProbability(70, 60, 80, Arrays.asList(75, 80), Arrays.asList(85, 90), Arrays.asList(65, 70));

        //test
        Boolean result = missionResultUtils.missionResult(mission, 1, userMembersStats);

        //results
        assertNotNull(result);
        assertEquals(expectedResult, result);

        assertEquals(Arrays.asList(75, 80), Arrays.asList(stats1.getSynchronizationRate(), stats2.getSynchronizationRate()));
        assertEquals(Arrays.asList(85, 90), Arrays.asList(stats1.getTacticalAbility(), stats2.getTacticalAbility()));
        assertEquals(Arrays.asList(65, 70), Arrays.asList(stats1.getSupportAbility(), stats2.getSupportAbility()));
    }
}
