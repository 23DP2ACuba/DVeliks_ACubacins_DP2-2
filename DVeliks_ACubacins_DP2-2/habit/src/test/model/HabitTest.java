package com.smarthabittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HabitTest {

    private Habit habit;

    @BeforeEach
    void setUp() {
        habit = new Habit("Test Habit", "Test Description");
    }

    @Test
    void testConstructorWithNameAndDescription() {
        assertEquals("Test Habit", habit.getName());
        assertEquals("Test Description", habit.getDescription());
        assertEquals(0, habit.getStreak());
        assertEquals(0, habit.getTotalCompletions());
        assertNull(habit.getLastCompletedDate());
    }

    @Test
    void testFullConstructor() {
        LocalDate date = LocalDate.of(2025, 5, 7);
        Habit fullHabit = new Habit("Full Habit", "Full Description", 5, 10, date);
        assertEquals("Full Habit", fullHabit.getName());
        assertEquals("Full Description", fullHabit.getDescription());
        assertEquals(5, fullHabit.getStreak());
        assertEquals(10, fullHabit.getTotalCompletions());
        assertEquals(date, fullHabit.getLastCompletedDate());
    }

    @Test
    void testSettersAndGetters() {
        habit.setName("New Name");
        habit.setDescription("New Description");
        habit.setStreak(3);
        habit.setTotalCompletions(5);
        LocalDate date = LocalDate.of(2025, 5, 7);
        habit.setLastCompletedDate(date);

        assertEquals("New Name", habit.getName());
        assertEquals("New Description", habit.getDescription());
        assertEquals(3, habit.getStreak());
        assertEquals(5, habit.getTotalCompletions());
        assertEquals(date, habit.getLastCompletedDate());
    }

    @Test
    void testIsCompletedToday_NotCompleted() {
        assertFalse(habit.isCompletedToday());
    }

    @Test
    void testIsCompletedToday_CompletedToday() {
        habit.setLastCompletedDate(LocalDate.now());
        assertTrue(habit.isCompletedToday());
    }

    @Test
    void testComplete_FirstCompletion() {
        habit.complete();
        assertEquals(1, habit.getStreak());
        assertEquals(1, habit.getTotalCompletions());
        assertEquals(LocalDate.now(), habit.getLastCompletedDate());
    }

    @Test
    void testComplete_ConsecutiveDay() {
        habit.setLastCompletedDate(LocalDate.now().minusDays(1));
        habit.setStreak(1);
        habit.setTotalCompletions(1);
        habit.complete();
        assertEquals(2, habit.getStreak());
        assertEquals(2, habit.getTotalCompletions());
        assertEquals(LocalDate.now(), habit.getLastCompletedDate());
    }

    @Test
    void testComplete_SameDay() {
        habit.setLastCompletedDate(LocalDate.now());
        habit.setStreak(1);
        habit.setTotalCompletions(1);
        habit.complete();
        assertEquals(1, habit.getStreak());
        assertEquals(1, habit.getTotalCompletions());
        assertEquals(LocalDate.now(), habit.getLastCompletedDate());
    }

    @Test
    void testComplete_NonConsecutiveDay() {
        habit.setLastCompletedDate(LocalDate.now().minusDays(2));
        habit.setStreak(1);
        habit.setTotalCompletions(1);
        habit.complete();
        assertEquals(1, habit.getStreak());
        assertEquals(2, habit.getTotalCompletions());
        assertEquals(LocalDate.now(), habit.getLastCompletedDate());
    }

    @Test
    void testToString() {
        habit.setStreak(3);
        habit.setTotalCompletions(5);
        habit.setLastCompletedDate(LocalDate.of(2025, 5, 7));
        String expected = "Test Habit,Test Description,3,5,2025-05-07";
        assertEquals(expected, habit.toString());
    }

    @Test
    void testToString_NullDate() {
        String expected = "Test Habit,Test Description,0,0,null";
        assertEquals(expected, habit.toString());
    }
}