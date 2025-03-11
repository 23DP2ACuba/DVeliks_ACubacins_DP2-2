package com.smarthabittracker.services;

import com.smarthabittracker.model.Habit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String DATA_DIRECTORY = "data";
    private static final String HABITS_FILE = DATA_DIRECTORY + "/habits.csv";

    static {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void saveHabits(List<Habit> habits) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HABITS_FILE))) {
            for (Habit habit : habits) {
                writer.write(formatHabitForCSV(habit));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving habits: " + e.getMessage());
        }
    }

    public static List<Habit> loadHabits() {
        List<Habit> habits = new ArrayList<>();
        
        File file = new File(HABITS_FILE);
        if (!file.exists()) {
            return habits;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(HABITS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Habit habit = parseHabitFromCSV(line);
                if (habit != null) {
                    habits.add(habit);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading habits: " + e.getMessage());
        }
        
        return habits;
    }

    private static String formatHabitForCSV(Habit habit) {
        return String.format("%s,%b,%s,%d,%s", 
            habit.getName(), 
            habit.isCompleted(), 
            habit.getCreatedDate(), 
            habit.getStreak(),
            habit.getDescription() != null ? habit.getDescription() : "");
    }

    private static Habit parseHabitFromCSV(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                Habit habit = new Habit(parts[0]);
                habit.setCompleted(Boolean.parseBoolean(parts[1]));
                habit.setDescription(parts.length > 4 ? parts[4] : "");
                return habit;
            }
        } catch (Exception e) {
            System.err.println("Error parsing habit from CSV: " + line);
        }
        return null;
    }
}