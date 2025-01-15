package com.company.NervManagementConsoleREST.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateUtilsTest {

    private CalculateUtils calculateUtils;

    @BeforeEach
    public void setup() {
        calculateUtils = new CalculateUtils();
    }

    @Test
    public void shouldReturnCorrectAverage_whenListIsNotEmpty() {
        //parameter
        List<Integer> values = Arrays.asList(10, 20, 30, 40, 50);

        //test
        Integer result = calculateUtils.calculateAverage(values);

        //result
        assertEquals(30, result);
    }

    @Test
    public void shouldReturnZero_whenListIsEmpty() {
        //parameter
        List<Integer> values = Collections.emptyList();

        //test
        Integer result = calculateUtils.calculateAverage(values);

        //result
        assertEquals(0, result);
    }

    @Test
    public void shouldReturnWin_whenProbabilityIsHigh() {
        //parameter
        Integer missionSr = 70, missionSa = 60, missionTa = 50;
        List<Integer> syncRate = Arrays.asList(60, 65, 70);
        List<Integer> tactAbility = Arrays.asList(50, 55, 60);
        List<Integer> suppAbility = Arrays.asList(40, 45, 50);

        //test
        boolean result = calculateUtils.calculateWinLoseProbability(missionSr, missionSa, missionTa, syncRate, tactAbility, suppAbility);

        //result
        assertNotNull(result);
    }

    @Test
    public void shouldReturnLose_whenProbabilityIsLow() {
        //parameter
        Integer missionSr = 90, missionSa = 80, missionTa = 70;
        List<Integer> syncRate = Arrays.asList(10, 15, 20);
        List<Integer> tactAbility = Arrays.asList(5, 10, 15);
        List<Integer> suppAbility = Arrays.asList(5, 10, 20);

        //test
        boolean result = calculateUtils.calculateWinLoseProbability(missionSr, missionSa, missionTa, syncRate, tactAbility, suppAbility);

        //result
        assertNotNull(result);
    }

    @Test
    public void shouldReturnMinValue_whenStatBelowZero() {
        //parameter
        Integer stat = -10;

        //test
        Integer result = calculateUtils.MinMaxStat(stat);

        //result
        assertEquals(0, result);
    }

    @Test
    public void shouldReturnMaxValue_whenStatAboveHundred() {
        //parameter
        Integer stat = 120;

        //test
        Integer result = calculateUtils.MinMaxStat(stat);

        //result
        assertEquals(100, result);
    }

    @Test
    public void shouldReturnSameValue_whenStatIsWithinRange() {
        //parameter
        Integer stat = 50;

        //test
        Integer result = calculateUtils.MinMaxStat(stat);

        //result
        assertEquals(50, result);
    }

    @Test
    public void shouldRandomizePositiveStats_whenMaxIsPositive() {
        //parameter
        Integer attrbMax = 100;

        //test
        Integer result = calculateUtils.randomizeStats(attrbMax);

        //result
        assertTrue(result > 0 && result <= 100);
    }

    @Test
    public void shouldRandomizeNegativeStats_whenMaxIsNegative() {
        //parameter
        Integer attrbMax = -50;

        //test
        Integer result = calculateUtils.randomizeStats(attrbMax);

        //result
        assertTrue(result >= -50 && result <= 0);
    }
}
