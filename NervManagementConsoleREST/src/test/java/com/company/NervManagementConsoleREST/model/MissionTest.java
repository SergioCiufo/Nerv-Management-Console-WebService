package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class MissionTest {
	private Mission mission = new Mission();

	@Test
	public void shouldReturnMissionId_whenGetMissionIdIsCalled_testCorrectMissionId() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		Integer missionId = mission.getMissionId();

		//result
		assertEquals(1, missionId);
	}

	@Test
	public void shouldSetMissionId_whenSetMissionIdIsCalled_testUpdatedMissionId() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setMissionId(2);

		//result
		assertEquals(2, mission.getMissionId());
	}

	@Test
	public void shouldReturnDescription_whenGetDescriptionIsCalled_testCorrectDescription() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		String description = mission.getDescription();

		//result
		assertEquals("Test", description);
	}

	@Test
	public void shouldSetDescription_whenSetDescriptionIsCalled_testUpdatedDescription() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setDescription("test");

		//result
		assertEquals("test", mission.getDescription());
	}

	@Test
	public void shouldReturnParticipantsMax_whenGetParticipantsMaxIsCalled_testCorrectParticipantsMax() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		Integer participantsMax = mission.getParticipantsMax();

		//result
		assertEquals(3, participantsMax);
	}

	@Test
	public void shouldSetParticipantsMax_whenSetParticipantsMaxIsCalled_testUpdatedParticipantsMax() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setParticipantsMax(3);

		//result
		assertEquals(3, mission.getParticipantsMax());
	}

	@Test
	public void shouldReturnEventMission_whenGetEventMissionIsCalled_testCorrectEventMission() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		Boolean eventMission = mission.getEventMission();

		//result
		assertTrue(eventMission);
	}

	@Test
	public void shouldSetEventMission_whenSetEventMissionIsCalled_testUpdatedEventMission() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setEventMission(false);

		//result
		assertFalse(mission.getEventMission());
	}

	@Test
	public void shouldReturnAvailable_whenGetAvailableIsCalled_testCorrectAvailable() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		Boolean available = mission.getAvailable();

		//result
		assertTrue(available);
	}

	@Test
	public void shouldSetAvailable_whenSetAvailableIsCalled_testUpdatedAvailable() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setAvailable(false);

		//result
		assertFalse(mission.getAvailable());
	}

	@Test
	public void shouldReturnReleaseDate_whenGetReleaseDateIsCalled_testCorrectReleaseDate() {
		//parameters
		LocalDate expectedReleaseDate = LocalDate.now();
		Mission mission = new Mission(1, " Test", null, 3, Collections.emptyList(), true, true, expectedReleaseDate);

		//test
		LocalDate releaseDate = mission.getReleaseDate();

		//result
		assertEquals(expectedReleaseDate, releaseDate);
	}

	@Test
	public void shouldSetReleaseDate_whenSetReleaseDateIsCalled_testUpdatedReleaseDate() {
		//parameters
		LocalDate newReleaseDate = LocalDate.of(2025, 1, 1);
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		mission.setReleaseDate(newReleaseDate);

		//result
		assertEquals(newReleaseDate, mission.getReleaseDate());
	}

	@Test
	public void shouldReturnHashCode_whenHashCodeIsCalled_testEqualHashCodes() {
		//parameters
		Mission mission1 = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());
		Mission mission2 = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//result
		assertEquals(mission1.hashCode(), mission2.hashCode());
	}

	@Test
	public void shouldReturnTrue_whenEqualsIsCalled_testEqualMissions() {
		//parameters
		Mission mission1 = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());
		Mission mission2 = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//result
		assertTrue(mission1.equals(mission2));
	}
/*
	@Test
	public void shouldReturnString_whenToStringIsCalled_testCorrectStringRepresentation() {
	    // parameters
	    Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());
	    mission.setName("Test");

	    // test
	    String result = mission.toString();

	    // result
	    String expected = "Mission [missionId=1, event=true, description=null, image=null, participantsMax=3, name=Test, durationTime=null, getExp()=null, getLevel()=null, getSynchronizationRate()=null, getTacticalAbility()=null, getSupportAbility()=null]";
	    assertEquals(expected, result);
	}
*/
	@Test
	public void shouldReturnNull_whenGetImageAsBase64IsCalled_testNullImage() {
		//parameters
		Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

		//test
		String base64Image = mission.getImageAsBase64();

		//result
		assertNull(base64Image);
	}

	@Test
	public void shouldSetImageFromBase64_whenSetImageFromBase64IsCalled_testCorrectImageSetting() throws Exception {
	    // parameters
	    Mission mission = new Mission(1, "Test", null, 3, Collections.emptyList(), true, true, LocalDate.now());

	    // test
	    mission.setImageFromBase64("base64");

	    // result
	    Blob imageBlob = mission.getImage();  // Assumendo che getImage() restituisca l'oggetto Blob
	    assertNotNull(imageBlob);  // Verifica che l'immagine sia stata impostata

	}
}
