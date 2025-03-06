module com.habit.tracker {
    requires javafx.controls;   // For basic JavaFX controls
    requires javafx.fxml;       // For FXML support
    requires java.base;         // Usually included implicitly, but good to be explicit

    exports com.smarthabittracker.ui;  // Export the package containing SmartHabitTrackerApp
}