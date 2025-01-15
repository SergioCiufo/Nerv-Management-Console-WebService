package com.company.NervManagementConsoleREST.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    //creiamo una sottoclasse concreta per testare Activity
    static class ActivityImpl extends Activity {
        public ActivityImpl(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility, Integer supportAbility, String name, Integer durationTime) {
            super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
        }
    }

    @Test
    public void shouldCreateActivity_whenConstructorIsCalled() {
        // parameters
        Integer exp = 100;
        Integer level = 1;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 75;
        Integer supportAbility = 65;
        String name = "Missione Test";
        Integer durationTime = 120;

        // test
        Activity activity = new ActivityImpl(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);

        // result
        assertEquals(exp, activity.getExp());
        assertEquals(level, activity.getLevel());
        assertEquals(synchronizationRate, activity.getSynchronizationRate());
        assertEquals(tacticalAbility, activity.getTacticalAbility());
        assertEquals(supportAbility, activity.getSupportAbility());
        assertEquals(name, activity.getName());
        assertEquals(durationTime, activity.getDurationTime());
    }

    @Test
    public void shouldSetValuesCorrectly_whenSettersAreCalled() {
        // parameters
        Activity activity = new ActivityImpl(null, null, null, null, null, null, null);
        Integer exp = 100;
        Integer level = 1;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 75;
        Integer supportAbility = 65;
        String name = "Nuova Missione";
        Integer durationTime = 150;

        // test
        activity.setExp(exp);
        activity.setLevel(level);
        activity.setSynchronizationRate(synchronizationRate);
        activity.setTacticalAbility(tacticalAbility);
        activity.setSupportAbility(supportAbility);
        activity.setName(name);
        activity.setDurationTime(durationTime);

        // result
        assertEquals(exp, activity.getExp());
        assertEquals(level, activity.getLevel());
        assertEquals(synchronizationRate, activity.getSynchronizationRate());
        assertEquals(tacticalAbility, activity.getTacticalAbility());
        assertEquals(supportAbility, activity.getSupportAbility());
        assertEquals(name, activity.getName());
        assertEquals(durationTime, activity.getDurationTime());
    }

    @Test
    public void shouldReturnTrue_whenEqualsIsCalledForEqualObjects() {
        // parameters
        Activity activity1 = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);
        Activity activity2 = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);

        // test
        boolean isEqual = activity1.equals(activity2);

        // result
        assertTrue(isEqual);
    }

    @Test
    public void shouldReturnFalse_whenEqualsIsCalledForDifferentObjects() {
        // parameters
        Activity activity1 = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);
        Activity activity2 = new ActivityImpl(150, 2, 70, 60, 80, "Missione A", 130);

        // test
        boolean isEqual = activity1.equals(activity2);

        // result
        assertFalse(isEqual);
    }

    @Test
    public void shouldReturnHashCode_whenHashCodeIsCalled() {
        // parameters
        Activity activity1 = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);
        Activity activity2 = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);

        // test
        assertEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void shouldReturnToString_whenToStringIsCalled() {
        // parameters
        Activity activity = new ActivityImpl(100, 1, 80, 75, 65, "Missione Test", 120);

        // test
        String expected = "Activity [name=Missione Test, durationTime=120]";
        assertEquals(expected.replaceAll("\\s", ""), activity.toString().replaceAll("\\s", "")); // rimuove gli spazi
    }
}
