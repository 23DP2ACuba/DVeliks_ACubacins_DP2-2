package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import com.smarthabittracker.services.HabitService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddHabitDialog extends Dialog<Boolean> {

    private final TextField nameField;
    private final TextArea descriptionArea;
    private final HabitService habitService;

    public AddHabitDialog(Stage owner, HabitService habitService) {
        this.habitService = habitService;
        
        setTitle("Add New Habit");
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        nameField = new TextField();
        nameField.setPromptText("Enter habit name");
        
        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter habit description");
        descriptionArea.setPrefRowCount(5);
        
        grid.add(new Label("Habit Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        
        getDialogPane().setContent(grid);
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);
        
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return saveHabit();
            }
            return false;
        });
    }
    
    private boolean saveHabit() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Habit name cannot be empty.");
            return false;
        }
        
        try {
            Habit habit = new Habit(name, description);
            habitService.addHabit(habit);
            return true;
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save habit: " + e.getMessage());
            return false;
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}