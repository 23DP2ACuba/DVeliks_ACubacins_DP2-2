package com.smarthabittracker.services;


import com.smarthabittracker.model.Habit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitService {
    private static final String DATA_FILE = "data.csv";
    
    public HabitService() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize data file: " + e.getMessage());
        }
    }
    
    public List<Habit> getAllHabits() throws IOException {
        List<Habit> habits = new ArrayList<>();
        
        if (Files.size(Paths.get(DATA_FILE)) == 0) {
            return habits;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    habits.add(parseHabit(line));
                }
            }
        }
        
        return habits;
    }
    
    public void addHabit(Habit habit) throws IOException {
        try (FileWriter writer = new FileWriter(DATA_FILE, true)) {
            writer.write(habit.toString() + "\n");
        }
    }
    
    public void deleteHabit(Habit habitToDelete) throws IOException {
        List<Habit> habits = getAllHabits();
        habits.removeIf(habit -> habit.getName().equals(habitToDelete.getName()));
        
        saveAllHabits(habits);
    }
    
    public void completeHabit(Habit habitToComplete) throws IOException {
        List<Habit> habits = getAllHabits();
        
        for (Habit habit : habits) {
            if (habit.getName().equals(habitToComplete.getName())) {
                habit.complete();
                break;
            }
        }
        
        saveAllHabits(habits);
    }
    
    private void saveAllHabits(List<Habit> habits) throws IOException {
        try (FileWriter writer = new FileWriter(DATA_FILE, false)) {
            for (Habit habit : habits) {
                writer.write(habit.toString() + "\n");
            }
        }
    }
    
    private Habit parseHabit(String line) {
        String[] parts = line.split(",", 4);
        
        String name = parts[0];
        String description = parts[1];
        int streak = Integer.parseInt(parts[2]);
        
        LocalDate lastCompletedDate = null;
        if (parts.length > 3 && !parts[3].equals("null")) {
            lastCompletedDate = LocalDate.parse(parts[3]);
        }
        
        return new Habit(name, description, streak, lastCompletedDate);
    }
}