package main.srv;


import java.io.*;
import java.util.*;

import main.model.Habit;

public class FileService {
    private static final String FILE_PATH = "data/habits.csv";
    
    public void saveToFile(List<Habit> habits) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Habit habit : habits) {
                writer.write(habit.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public List<Habit> loadFromFile() {
        List<Habit> habits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                habits.add(Habit.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("No previous data found, starting fresh.");
        }
        return habits;
    }
}