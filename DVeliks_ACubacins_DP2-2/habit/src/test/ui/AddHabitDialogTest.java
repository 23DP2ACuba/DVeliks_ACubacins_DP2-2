package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import com.smarthabittracker.services.HabitService;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class AddHabitDialogTest {

    private AddHabitDialog dialog;
    private HabitService habitService;
    private Stage stage;

    @Start
    void start(Stage stage) {
        this.stage = stage;
        habitService = mock(HabitService.class);
        dialog = new AddHabitDialog(stage, habitService);
    }

    @BeforeEach
    void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(s -> dialog.show());
    }

    @Test
    void testSaveHabit_Success(FxRobot robot) throws IOException, TimeoutException {
        robot.lookup("#nameField").queryAs(TextField.class).setText("New Habit");
        robot.lookup("#descriptionArea").queryAs(TextArea.class).setText("New Description");
        robot.clickOn("Save");

        FxToolkit.runLaterAndWait(() -> {
            verify(habitService).addHabit(any(Habit.class));
            assertTrue(dialog.getResult());
        });
    }

    @Test
    void testSaveHabit_EmptyName(FxRobot robot) throws TimeoutException {
        robot.lookup("#nameField").queryAs(TextField.class).setText("");
        robot.lookup("#descriptionArea").queryAs(TextArea.class).setText("Description");
        robot.clickOn("Save");

        FxToolkit.runLaterAndWait(() -> assertFalse(dialog.getResult()));
    }

    @Test
    void testSaveHabit_IOException(FxRobot robot) throws IOException, TimeoutException {
        doThrow(new IOException("Test Exception")).when(habitService).addHabit(any(Habit.class));
        robot.lookup("#nameField").queryAs(TextField.class).setText("New Habit");
        robot.lookup("#descriptionArea").queryAs(TextArea.class).setText("Description");
        robot.clickOn("Save");

        FxToolkit.runLaterAndWait(() -> assertFalse(dialog.getResult()));
    }

    @Test
    void testCancel(FxRobot robot) throws TimeoutException {
        robot.clickOn("Cancel");

        FxToolkit.runLaterAndWait(() -> assertFalse(dialog.getResult()));
    }
}