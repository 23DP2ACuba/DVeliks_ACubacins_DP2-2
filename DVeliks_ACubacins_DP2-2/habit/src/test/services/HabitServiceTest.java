package com.smarthabittracker.services;

import com.smarthabittracker.model.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitServiceTest {

    private HabitService habitService;
    private File dataFile;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        dataFile = new File(tempDir.toFile(), "data.csv");
        habitService = new HabitService() {
            @Override
            protected String getDataFilePath() {
                return dataFile.getAbsolutePath();
            }
        };
    }

    @Test
    void testConstructor_CreatesFile() {
        assertTrue(dataFile.exists());
    }

    @Test
    void testGetAllHabits_EmptyFile() throws IOException {
        List<Habit> habits = habitService.getAllHabits();
        assertTrue(habits.isEmpty());
    }

    @Test
    void testGetAllHabits_WithData() throws IOException {
        try (FileWriter writer = new FileWriter(dataFile)) {
            writer.write("Test Habit,Test Description,2,3,2025-05-07\n");
        }
        List<Habit> habits = habitService.getAllHabits();
        assertEquals(1, habits.size());
        Habit habit = habits.get(0);
        assertEquals("Test Habit", habit.getName());
        assertEquals("Test Description", habit.getDescription());
        assertEquals(2, habit.getStreak());
        assertEquals(3, habit.getTotalCompletions());
        assertEquals(LocalDate.of(2025, 5, 7), habit.getLastCompletedDate());
    }

    @Test
    void testAddHabit() throws IOException {
        Habit habit = new Habit("New Habit", "New Description");
        habitService.addHabit(habit);
        List<Habit> habits = habitService.getAllHabits();
        assertEquals(1, habits.size());
        assertEquals("New Habit", habits.get(0).getName());
    }

    @Test
    void testDeleteHabit() throws IOException {
        Habit habit = new Habit("Habit to Delete", "Description");
        habitService.addHabit(habit);
        habitService.deleteHabit(habit);
        List<Habit> habits = habitService.getAllHabits();
        assertTrue(habits.isEmpty());
    }

    @Test
    void testCompleteHabit() throws IOException {
        Habit habit = new Habit("Habit to Complete", "Description");
        habitService.addHabit(habit);
        habitService.completeHabit(habit);
        List<Habit> habits = habitService.getAllHabits();
        assertEquals(1, habits.size());
        assertEquals(1, habits.get(0).getStreak());
        assertEquals(1, habits.get(0).getTotalCompletions());
        assertEquals(LocalDate.now(), habits.get(0).getLastCompletedDate());
    }

    @Test
    void testGetAverageStreak_EmptyList() {
        assertEquals(0.0, habitService.getAverageStreak(List.of()));
    }

    @Test
    void testGetAverageStreak_WithHabits() {
        Habit habit1 = new Habit("Habit1", "Desc1");
        habit1.setStreak(2);
        Habit habit2 = new Habit("Habit2", "Desc2");
        habit2.setStreak(4);
        double average = habitService.getAverageStreak(List.of(habit1, habit2));
        assertEquals(3.0, average);
    }

    @Test
    void testGetTotalCompletions() {
        Habit habit1 = new Habit("Habit1", "Desc1");
        habit1.setTotalCompletions(5);
        Habit habit2 = new Habit("Habit2", "Desc2");
        habit2.setTotalCompletions(3);
        int total = habitService.getTotalCompletions(List.of(habit1, habit2));
        assertEquals(8, total);
    }
}