package com.smarthabittracker.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SmartHabitTrackerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Loading FXML: " + getClass().getResource("resources/com/smarthabittracker/ui/MainHabitTracker.fxml"));
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/com/smarthabittracker/ui/MainHabitTracker.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("FXML file not found!");
        }
    
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Smart Habit Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}