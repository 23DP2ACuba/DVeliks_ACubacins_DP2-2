module com.smarthabittracker {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.smarthabittracker to javafx.fxml;
    opens com.smarthabittracker.ui to javafx.fxml;
    opens com.smarthabittracker.model to javafx.base;

    exports com.smarthabittracker;
    exports com.smarthabittracker.ui;
    exports com.smarthabittracker.model;
    exports com.smarthabittracker.services;
}