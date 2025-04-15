package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import com.smarthabittracker.services.HabitService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HabitTrackerUI extends Application {

    private TableView<Habit> habitTable;
    private ObservableList<Habit> habitData;
    private HabitService habitService;
    private Stage primaryStage;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.habitService = new HabitService();
        
        primaryStage.setTitle("Smart Habit Tracker");
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        VBox topSection = createTopSection();
        root.setTop(topSection);
        
        habitTable = createHabitTable();
        root.setCenter(habitTable);
        
        HBox bottomSection = createBottomSection();
        root.setBottom(bottomSection);
        
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        
        loadHabits();
        
        primaryStage.show();
    }
    
    private VBox createTopSection() {
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        
        Label title = new Label("Smart Habit Tracker");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #4a86e8;");
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addHabitButton = new Button("Add Habit");
        addHabitButton.setOnAction(e -> showAddHabitDialog());
        
        Button viewHabitsButton = new Button("View Habits");
        viewHabitsButton.setOnAction(e -> {
            Habit selectedHabit = habitTable.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                showViewHabitDialog(selectedHabit);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Selection", 
                          "Please select a habit to view.");
            }
        });
        
        buttonBox.getChildren().addAll(addHabitButton, viewHabitsButton);
        topSection.getChildren().addAll(title, buttonBox);
        
        return topSection;
    }
    
    private TableView<Habit> createHabitTable() {
        TableView<Habit> table = new TableView<>();
        
        TableColumn<Habit, String> nameColumn = new TableColumn<>("Habit Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(150);
        
        TableColumn<Habit, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("completedToday"));
        statusColumn.setPrefWidth(100);
        
        TableColumn<Habit, Integer> streakColumn = new TableColumn<>("Streak");
        streakColumn.setCellValueFactory(new PropertyValueFactory<>("streak"));
        streakColumn.setPrefWidth(80);
        
        TableColumn<Habit, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(200);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button completeButton = new Button("Complete");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(5, completeButton, deleteButton);
            
            {
                completeButton.setOnAction(event -> {
                    Habit habit = getTableView().getItems().get(getIndex());
                    try {
                        habitService.completeHabit(habit);
                        loadHabits();
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", 
                                  "Failed to complete habit: " + e.getMessage());
                    }
                });
                
                deleteButton.setOnAction(event -> {
                    Habit habit = getTableView().getItems().get(getIndex());
                    try {
                        habitService.deleteHabit(habit);
                        loadHabits(); 
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", 
                                  "Failed to delete habit: " + e.getMessage());
                    }
                });
                
                pane.setAlignment(Pos.CENTER);
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
        
        table.getColumns().addAll(nameColumn, statusColumn, streakColumn, actionsColumn);
        
        return table;
    }
    
    private HBox createBottomSection() {
        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(10, 0, 0, 0));
        
        statusLabel = new Label("Total Habits: 0  Completed: 0");
        bottomSection.getChildren().add(statusLabel);
        
        return bottomSection;
    }
    
    private void showAddHabitDialog() {
        AddHabitDialog dialog = new AddHabitDialog(primaryStage, habitService);
        dialog.showAndWait().ifPresent(result -> {
            if (result) {
                loadHabits(); 
            }
        });
    }
    
    private void showViewHabitDialog(Habit habit) {
        ViewHabitDialog dialog = new ViewHabitDialog(primaryStage, habit);
        dialog.show();
    }
    
    private void loadHabits() {
        try {
            List<Habit> habits = habitService.getAllHabits();
            habitData = FXCollections.observableArrayList(habits);
            habitTable.setItems(habitData);
            
            int totalHabits = habits.size();
            int completedHabits = (int) habits.stream()
                    .filter(Habit::isCompletedToday)
                    .count();
            statusLabel.setText("Total Habits: " + totalHabits + "  Completed: " + completedHabits);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", 
                      "Failed to load habits: " + e.getMessage());
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