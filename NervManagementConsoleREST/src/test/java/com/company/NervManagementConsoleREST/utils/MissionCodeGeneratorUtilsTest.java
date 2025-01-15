package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.model.MissionArchive;

public class MissionCodeGeneratorUtilsTest {
	private MissionCodeGeneratorUtils missionCodeGeneratorUtils = new MissionCodeGeneratorUtils();
	
	@Test
	public void shouldReturnMissionCode_whenAllOk() throws Exception{
		//parameters
		List<MissionArchive> missionArchive = new ArrayList<>();
		
		MissionArchive archive1 = new MissionArchive();
        archive1.setMissionCode("M1-001");
        missionArchive.add(archive1);
        
        MissionArchive archive2 = new MissionArchive();
        archive2.setMissionCode("M1-002");
        missionArchive.add(archive2);
        
        Integer idMission = 1;	
		
		//test
        String result = missionCodeGeneratorUtils.missionCodeGenerator(missionArchive, idMission);
		
		//results
        assertNotNull(result);
        assertEquals("M1-003", result);
	}
	
	@Test
    public void shouldReturnMissionCode_whenArchiveIsEmpty() {
		//parameters
        List<MissionArchive> missionArchive = new ArrayList<>();

        Integer idMission = 1;

        //test
        String result = missionCodeGeneratorUtils.missionCodeGenerator(missionArchive, idMission);

        //results
        assertNotNull(result);
        assertEquals("M1-001", result);
    }

    @Test
    public void shouldReturnNull_whenExceptionOccurs() {
    	//parameters
        List<MissionArchive> missionArchive = null;

        Integer idMission = 1;

        //test
        String result = missionCodeGeneratorUtils.missionCodeGenerator(missionArchive, idMission);

        //results
        assertNotNull(result);
        assertEquals("M1-001", result);
    }
    
    @Test
    public void shouldHandleException_whenInvalidMissionCode() {
        //parameters
        List<MissionArchive> missionArchive = new ArrayList<>();
        Integer idMission = 1;

        MissionArchive archive = new MissionArchive();
        archive.setMissionCode(null); //eccezione durante il parsing
        missionArchive.add(archive);

        //test
        String result = missionCodeGeneratorUtils.missionCodeGenerator(missionArchive, idMission);

        //results
        assertNull(result);
    }
}
