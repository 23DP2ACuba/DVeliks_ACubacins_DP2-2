package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Habit;
import services.HabitService;

public class AddHabitDialogController {
    @FXML private TextField habitNameField;
    @FXML private TextArea habitDescriptionArea;

    private Stage dialogStage;
    private HabitService habitService;
    private HabitTrackerController mainController;

    public AddHabitDialogController() {
        habitService = new HabitService();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainController(HabitTrackerController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void saveHabit() {
        String habitName = habitNameField.getText();
        String habitDescription = habitDescriptionArea.getText();

        if (habitName != null && !habitName.trim().isEmpty()) {
            Habit newHabit = new Habit(habitName);
            newHabit.setDescription(habitDescription);
            habitService.addHabit(newHabit);
            
            // Refresh the main habit list
            mainController.showHabitList();
            
            dialogStage.close();
        } else {
            // Show error that habit name is required
            // You could use an Alert dialog here
        }
    }

    @FXML
    private void cancelDialog() {
        dialogStage.close();
    }
}