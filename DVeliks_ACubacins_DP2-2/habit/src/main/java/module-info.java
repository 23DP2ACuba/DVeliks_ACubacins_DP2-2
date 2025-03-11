module com.habit.tracker {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.smarthabittracker.ui to javafx.fxml; 

    exports com.smarthabittracker.ui;
}