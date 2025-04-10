package com.smarthabittracker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.smarthabittracker.model.Habit;
import com.smarthabittracker.services.HabitService;

public class AddHabitDialogController {
    @FXML private TextField habitNameField;
    @FXML private TextArea habitDescriptionArea;
    private Stage dialogStage;
    private HabitTrackerController mainController;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainController(HabitTrackerController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void saveHabit() {
        Habit newHabit = new Habit(habitNameField.getText());
        newHabit.setDescription(habitDescriptionArea.getText());
        mainController.habitService.addHabit(newHabit);
        mainController.refreshHabitList();
        dialogStage.close();
    }

    @FXML
    private void cancelDialog() {
        dialogStage.close();
    }
}