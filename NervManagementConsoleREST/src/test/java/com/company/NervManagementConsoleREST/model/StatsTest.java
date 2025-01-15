package com.company.NervManagementConsoleREST.model;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    //creiamo una sottoclasse contreta così possiamo istanziarla (per il test)
    static class StatsImpl extends Stats {
        public StatsImpl(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility, Integer supportAbility) {
            super(exp, level, synchronizationRate, tacticalAbility, supportAbility);
        }
    }

    @Test
    public void shouldCreateStats_whenConstructorIsCalled() {
        // parameters
        Integer exp = 100;
        Integer level = 1;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 75;
        Integer supportAbility = 65;

        // test
        Stats stats = new StatsImpl(exp, level, synchronizationRate, tacticalAbility, supportAbility);

        // result
        assertEquals(exp, stats.getExp());
        assertEquals(level, stats.getLevel());
        assertEquals(synchronizationRate, stats.getSynchronizationRate());
        assertEquals(tacticalAbility, stats.getTacticalAbility());
        assertEquals(supportAbility, stats.getSupportAbility());
    }

    @Test
    public void shouldSetValuesCorrectly_whenSettersAreCalled() {
        // parameters
        Stats stats = new StatsImpl(null, null, null, null, null);
        Integer exp = 100;
        Integer level = 1;
        Integer synchronizationRate = 80;
        Integer tacticalAbility = 75;
        Integer supportAbility = 65;

        // test
        stats.setExp(exp);
        stats.setLevel(level);
        stats.setSynchronizationRate(synchronizationRate);
        stats.setTacticalAbility(tacticalAbility);
        stats.setSupportAbility(supportAbility);

        // result
        assertEquals(exp, stats.getExp());
        assertEquals(level, stats.getLevel());
        assertEquals(synchronizationRate, stats.getSynchronizationRate());
        assertEquals(tacticalAbility, stats.getTacticalAbility());
        assertEquals(supportAbility, stats.getSupportAbility());
    }

    @Test
    public void shouldReturnTrue_whenEqualsIsCalledForEqualObjects() {
        // parameters
        Stats stats1 = new StatsImpl(100, 1, 80, 75, 65);
        Stats stats2 = new StatsImpl(100, 1, 80, 75, 65);

        // test
        boolean isEqual = stats1.equals(stats2);

        // result
        assertTrue(isEqual);
    }

    @Test
    public void shouldReturnFalse_whenEqualsIsCalledForDifferentObjects() {
        // parameters
        Stats stats1 = new StatsImpl(100, 1, 80, 75, 65);
        Stats stats2 = new StatsImpl(150, 2, 70, 60, 80);

        // test
        boolean isEqual = stats1.equals(stats2);

        // result
        assertFalse(isEqual);
    }

    @Test
    public void shouldReturnHashCode_whenHashCodeIsCalled() {
        // parameters
        Stats stats1 = new StatsImpl(100, 1, 80, 75, 65);
        Stats stats2 = new StatsImpl(100, 1, 80, 75, 65);

        // test
        assertEquals(stats1.hashCode(), stats2.hashCode());
    }

    @Test
    public void shouldReturnToString_whenToStringIsCalled() {
        // parameters
        Stats stats = new StatsImpl(100, 1, 80, 75, 65);

        // test
        String expected = "Stats [ exp=100, level=1, synchronizationRate=80, tacticalAbility=75, supportAbility=65 ]";
        assertEquals(expected.replaceAll("\\s", ""), stats.toString().replaceAll("\\s", "")); //rimuove gli spazi
    }
}