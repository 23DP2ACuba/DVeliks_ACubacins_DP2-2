package model;

import java.time.LocalDate;
import java.util.Objects;

public class Habit {
    private String name;
    private boolean completed;
    private LocalDate createdDate;
    private int streak;
    private String description;

    // Default constructor
    public Habit() {
        this.createdDate = LocalDate.now();
        this.streak = 0;
        this.completed = false;
    }

    // Parameterized constructor
    public Habit(String name) {
        this();
        this.name = name;
    }

    // Full constructor
    public Habit(String name, String description) {
        this(name);
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.streak++;
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public int getStreak() {
        return streak;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ToString method for easy printing
    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", completed=" + completed +
                ", streak=" + streak +
                ", description='" + description + '\'' +
                '}';
    }

    // Equals and HashCode for comparing habits
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(name, habit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}