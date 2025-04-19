package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
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
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label nameLabel = new Label("Habit: " + habit.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(5);
        statsGrid.setPadding(new Insets(5));
        
        statsGrid.add(new Label("Current Streak:"), 0, 0);
        statsGrid.add(new Label(habit.getStreak() + " days"), 1, 0);
        
        statsGrid.add(new Label("Total Completions:"), 0, 1);
        statsGrid.add(new Label(String.valueOf(habit.getTotalCompletions())), 1, 1);
        
        String lastCompleted = habit.getLastCompletedDate() != null ? 
                               habit.getLastCompletedDate().toString() : "Never";
        statsGrid.add(new Label("Last Completed:"), 0, 2);
        statsGrid.add(new Label(lastCompleted), 1, 2);
        
        TextArea descriptionArea = new TextArea(habit.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(10);
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialogStage.close());
        
        root.getChildren().addAll(nameLabel, statsGrid, new Label("Description:"), 
                                 descriptionArea, closeButton);
        
        Scene scene = new Scene(root, 400, 350);
        dialogStage.setScene(scene);
    }
    
    public void show() {
        dialogStage.show();
    }
}