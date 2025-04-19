package com.smarthabittracker.model;

import java.time.LocalDate;

public class Habit {
    private String name;
    private String description;
    private int streak;
    private int totalCompletions;
    private LocalDate lastCompletedDate;
    
    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.streak = 0;
        this.totalCompletions = 0;
        this.lastCompletedDate = null;
    }
    
    public Habit(String name, String description, int streak, int totalCompletions, LocalDate lastCompletedDate) {
        this.name = name;
        this.description = description;
        this.streak = streak;
        this.totalCompletions = totalCompletions;
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
    
    public int getTotalCompletions() {
        return totalCompletions;
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
    
    public void setTotalCompletions(int totalCompletions) {
        this.totalCompletions = totalCompletions;
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
            totalCompletions = 1;
        } else if (today.equals(lastCompletedDate)) {
        } else if (today.equals(lastCompletedDate.plusDays(1))) {
            streak++;
            totalCompletions++;
        } else {
            streak = 1;
            totalCompletions++;
        }
        
        lastCompletedDate = today;
    }
    
    @Override
    public String toString() {
        return name + "," + description + "," + streak + "," + totalCompletions + "," + 
               (lastCompletedDate != null ? lastCompletedDate.toString() : "null");
    }
}