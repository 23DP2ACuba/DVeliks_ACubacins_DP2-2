package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Habit;
import services.HabitService;

import java.io.IOException;

public class HabitTrackerController {
    @FXML private TableView<Habit> habitTableView;
    @FXML private TableColumn<Habit, String> nameColumn;
    @FXML private TableColumn<Habit, Boolean> statusColumn;
    @FXML private TableColumn<Habit, Integer> streakColumn;
    @FXML private TableColumn<Habit, Void> actionsColumn;
    @FXML private Label totalHabitsLabel;
    @FXML private Label completedHabitsLabel;

    private HabitService habitService;

    @FXML
    public void initialize() {
        habitService = new HabitService();
        setupTableColumns();
        refreshHabitList();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().completedProperty());
        streakColumn.setCellValueFactory(cellData -> cellData.getValue().streakProperty().asObject());

        // Add action buttons to the actions column
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button completeButton = new Button("Complete");
            private final Button deleteButton = new Button("Delete");

            {
                completeButton.setOnAction(event -> {
                    Habit habit = getTableView().getItems().get(getIndex());
                    habit.setCompleted(true);
                    habitService.updateHabit(habit);
                    refreshHabitList();
                });

                deleteButton.setOnAction(event -> {
                    Habit habit = getTableView().getItems().get(getIndex());
                    habitService.removeHabit(habit);
                    refreshHabitList();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, completeButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void refreshHabitList() {
        habitTableView.getItems().setAll(habitService.getAllHabits());
        totalHabitsLabel.setText(String.valueOf(habitService.getAllHabits().size()));
        completedHabitsLabel.setText(String.valueOf(
            habitService.getAllHabits().stream()
                .filter(Habit::isCompleted)
                .count()
        ));
    }

    @FXML
    public void openAddHabitDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddHabitDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add New Habit");
            dialogStage.setScene(new Scene(root));

            AddHabitDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showHabitList() {
        // Refresh the habit list (already done in initialize method)
        refreshHabitList();
    }
}