package com.smarthabittracker.services;

import com.smarthabittracker.model.Habit;
import com.smarthabittracker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabitService {
    private List<Habit> habits;

    public HabitService() {
        // Load existing habits from file
        this.habits = FileService.loadHabits();
    }

    // Add a new habit
    public void addHabit(Habit habit) {
        // Prevent duplicate habits
        if (!habits.contains(habit)) {
            habits.add(habit);
            saveHabits();
        }
    }

    // Remove a habit
    public void removeHabit(Habit habit) {
        habits.remove(habit);
        saveHabits();
    }

    // Get all habits
    public List<Habit> getAllHabits() {
        return new ArrayList<>(habits);
    }

    // Find habit by name
    public Habit findHabitByName(String name) {
        return habits.stream()
            .filter(habit -> habit.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    // Update habit status
    public void updateHabitStatus(String habitName, boolean completed) {
        Habit habit = findHabitByName(habitName);
        if (habit != null) {
            habit.setCompleted(completed);
            saveHabits();
        }
    }

    // Get completed habits
    public List<Habit> getCompletedHabits() {
        return habits.stream()
            .filter(Habit::isCompleted)
            .collect(Collectors.toList());
    }

    // Get pending habits
    public List<Habit> getPendingHabits() {
        return habits.stream()
            .filter(habit -> !habit.isCompleted())
            .collect(Collectors.toList());
    }

    // Save habits to file
    private void saveHabits() {
        FileService.saveHabits(habits);
    }
}