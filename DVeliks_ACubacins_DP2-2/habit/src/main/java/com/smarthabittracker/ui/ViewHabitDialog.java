package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewHabitDialog {

    private final Stage dialogStage;
    private final Habit habit;

    public ViewHabitDialog(Stage owner, Habit habit) {
        this.habit = habit;
        
        dialogStage = new Stage();
        dialogStage.setTitle("View Habit: " + habit.getName());
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        
        // Create dialog content
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label nameLabel = new Label("Habit: " + habit.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        Label streakLabel = new Label("Current Streak: " + habit.getStreak() + " days");
        
        TextArea descriptionArea = new TextArea(habit.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(10);
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialogStage.close());
        
        root.getChildren().addAll(nameLabel, streakLabel, new Label("Description:"), 
                                 descriptionArea, closeButton);
        
        Scene scene = new Scene(root, 400, 300);
        dialogStage.setScene(scene);
    }
    
    public void show() {
        dialogStage.show();
    }
}