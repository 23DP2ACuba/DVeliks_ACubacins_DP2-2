package com.smarthabittracker.services;

import com.smarthabittracker.model.Habit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabitService {
    private List<Habit> habits;

    public HabitService() {
        this.habits = FileService.loadHabits();
    }

    public void addHabit(Habit habit) {
        if (!habits.contains(habit)) {
            habits.add(habit);
            saveHabits();
        }
    }

    public void removeHabit(Habit habit) {
        habits.remove(habit);
        saveHabits();
    }

    public void updateHabit(Habit habit) {
        int index = habits.indexOf(habit);
        if (index != -1) {
            habits.set(index, habit);
            saveHabits();
        }
    }    

    public List<Habit> getAllHabits() {
        return new ArrayList<>(habits);
    }

    public Habit findHabitByName(String name) {
        return habits.stream()
            .filter(habit -> habit.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public void updateHabitStatus(String habitName, boolean completed) {
        Habit habit = findHabitByName(habitName);
        if (habit != null) {
            habit.setCompleted(completed);
            saveHabits();
        }
    }

    public List<Habit> getCompletedHabits() {
        return habits.stream()
            .filter(Habit::isCompleted)
            .collect(Collectors.toList());
    }

    public List<Habit> getPendingHabits() {
        return habits.stream()
            .filter(habit -> !habit.isCompleted())
            .collect(Collectors.toList());
    }

    private void saveHabits() {
        FileService.saveHabits(habits);
    }
}