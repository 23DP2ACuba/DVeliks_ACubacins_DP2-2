package ui;

import model.Habit;
import services.HabitService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private HabitService habitService;
    private Scanner scanner;

    public ConsoleUI() {
        this.habitService = new HabitService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addNewHabit();
                    break;
                case 2:
                    markHabitCompleted();
                    break;
                case 3:
                    viewAllHabits();
                    break;
                case 4:
                    removeHabit();
                    break;
                case 5:
                    viewCompletedHabits();
                    break;
                case 6:
                    viewPendingHabits();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Smart Habit Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Smart Habit Tracker ---");
        System.out.println("1. Add a new habit");
        System.out.println("2. Mark a habit as completed");
        System.out.println("3. View all habits");
        System.out.println("4. Remove a habit");
        System.out.println("5. View completed habits");
        System.out.println("6. View pending habits");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addNewHabit() {
        System.out.print("Enter habit name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter habit description (optional): ");
        String description = scanner.nextLine();
        
        Habit newHabit = new Habit(name, description);
        habitService.addHabit(newHabit);
        System.out.println("Habit added successfully: " + name);
    }

    private void markHabitCompleted() {
        System.out.print("Enter habit name to mark as completed: ");
        String name = scanner.nextLine();
        habitService.updateHabitStatus(name, true);
        System.out.println("Habit marked as completed: " + name);
    }

    private void viewAllHabits() {
        List<Habit> habits = habitService.getAllHabits();
        if (habits.isEmpty()) {
            System.out.println("No habits found.");
            return;
        }
        
        System.out.println("\n--- Current Habits ---");
        habits.forEach(habit -> 
            System.out.printf("Name: %s | Completed: %b | Streak: %d | Description: %s%n", 
                habit.getName(), 
                habit.isCompleted(), 
                habit.getStreak(),
                habit.getDescription() != null ? habit.getDescription() : "N/A")
        );
    }

    private void removeHabit() {
        System.out.print("Enter habit name to remove: ");
        String name = scanner.nextLine();
        Habit habit = habitService.findHabitByName(name);
        
        if (habit != null) {
            habitService.removeHabit(habit);
            System.out.println("Habit removed: " + name);
        } else {
            System.out.println("Habit not found: " + name);
        }
    }

    private void viewCompletedHabits() {
        List<Habit> completedHabits = habitService.getCompletedHabits();
        if (completedHabits.isEmpty()) {
            System.out.println("No completed habits found.");
            return;
        }
        
        System.out.println("\n--- Completed Habits ---");
        completedHabits.forEach(habit -> 
            System.out.printf("Name: %s | Streak: %d | Description: %s%n", 
                habit.getName(), 
                habit.getStreak(),
                habit.getDescription() != null ? habit.getDescription() : "N/A")
        );
    }

    private void viewPendingHabits() {
        List<Habit> pendingHabits = habitService.getPendingHabits();
        if (pendingHabits.isEmpty()) {
            System.out.println("No pending habits found.");
            return;
        }
        
        System.out.println("\n--- Pending Habits ---");
        pendingHabits.forEach(habit -> 
            System.out.printf("Name: %s | Description: %s%n", 
                habit.getName(),
                habit.getDescription() != null ? habit.getDescription() : "N/A")
        );
    }
}