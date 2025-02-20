package main.ui;
import java.util.Scanner;
public class ConsoleUI {
    private final HabitService habitService;
    private final Scanner scanner;

    public ConsoleUI() {
        habitService = new HabitService();
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nSmart Habit Tracker");
            System.out.println("1. Add Habit");
            System.out.println("2. Mark Habit as Completed");
            System.out.println("3. Show Habits");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addHabit();
                case 2 -> markHabitCompleted();
                case 3 -> showHabits();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void addHabit() {
        System.out.print("Enter habit name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        habitService.addHabit(new Habit(name, category));
        System.out.println("Habit added!");
    }

    private void markHabitCompleted() {
        System.out.print("Enter habit name to mark as completed: ");
        String name = scanner.nextLine();
        habitService.markHabitCompleted(name);
        System.out.println("Habit marked as completed!");
    }

    private void showHabits() {
        System.out.println("\nYour Habits:");
        for (Habit habit : habitService.getAllHabits()) {
            System.out.println("- " + habit.getName() + " | Streak: " + habit.getStreak());
        }
    }
}