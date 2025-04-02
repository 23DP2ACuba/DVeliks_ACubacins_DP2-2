package com.smarthabittracker.model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.Objects;

public class Habit {
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty completed = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> createdDate = new SimpleObjectProperty<>();
    private final IntegerProperty streak = new SimpleIntegerProperty(0);
    private final StringProperty description = new SimpleStringProperty();

    public Habit() {
        this.createdDate.set(LocalDate.now());
    }

    public Habit(String name) {
        this();
        this.name.set(name);
    }

    public Habit(String name, String description) {
        this(name);
        this.description.set(description);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public ObjectProperty<LocalDate> createdDateProperty() {
        return createdDate;
    }

    public IntegerProperty streakProperty() {
        return streak;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(boolean completed) {
        if (completed) { 
            this.completed.set(completed);
            this.streak.set(this.streak.get() + 1);
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate.get();
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate.set(createdDate);
    }

    public int getStreak() {
        return streak.get();
    }

    public void setStreak(int streak) {
        this.streak.set(streak);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + getName() + '\'' +
                ", completed=" + isCompleted() +
                ", createdDate=" + getCreatedDate() +
                ", streak=" + getStreak() +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(getName(), habit.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}