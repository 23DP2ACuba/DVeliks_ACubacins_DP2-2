package com.smarthabittracker.model;

import java.time.LocalDate;

public class Habit {
    private String name;
    private String description;
    private int streak;
    private LocalDate lastCompletedDate;
    
    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.streak = 0;
        this.lastCompletedDate = null;
    }
    
    public Habit(String name, String description, int streak, LocalDate lastCompletedDate) {
        this.name = name;
        this.description = description;
        this.streak = streak;
        this.lastCompletedDate = lastCompletedDate;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getStreak() {
        return streak;
    }
    
    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setStreak(int streak) {
        this.streak = streak;
    }
    
    public void setLastCompletedDate(LocalDate lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }
    
    public boolean isCompletedToday() {
        return lastCompletedDate != null && lastCompletedDate.equals(LocalDate.now());
    }
    
    public void complete() {
        LocalDate today = LocalDate.now();
        
        if (lastCompletedDate == null) {
            streak = 1;
        } else if (today.equals(lastCompletedDate)) {
        } else if (today.equals(lastCompletedDate.plusDays(1))) {
            streak++;
        } else {
            streak = 1;
        }
        
        lastCompletedDate = today;
    }
    
    @Override
    public String toString() {
        return name + "," + description + "," + streak + "," + 
               (lastCompletedDate != null ? lastCompletedDate.toString() : "null");
    }
}