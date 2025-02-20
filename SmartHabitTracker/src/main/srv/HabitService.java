package main.srv;

import java.util.*;

public class HabitService {
    private final List<Habit> habits;
    private final FileService fileService;

    public HabitService() {
        fileService = new FileService();
        habits = fileService.loadFromFile();
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
        fileService.saveToFile(habits);
    }

    public void markHabitCompleted(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equalsIgnoreCase(name)) {
                habit.markCompleted();
                fileService.saveToFile(habits);
                break;
            }
        }
    }

    public List<Habit> getAllHabits() {
        return habits;
    }
}
