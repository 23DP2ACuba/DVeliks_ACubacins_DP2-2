package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class ViewHabitDialogTest {

    private ViewHabitDialog dialog;
    private Habit habit;
    private Stage stage;

    @Start
    void start(Stage stage) {
        this.stage = stage;
        habit = new Habit("Test Habit", "Test Description");
        habit.setStreak(3);
        habit.setTotalCompletions(5);
        habit.setLastCompletedDate(LocalDate.of(2025, 5, 7));
        dialog = new ViewHabitDialog(stage, habit);
    }

    @BeforeEach
    void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    void testDialogInitialization() throws TimeoutException {
        FxToolkit.runLaterAndWait(() -> {
            assertEquals("View Habit: Test Habit", dialog.getDialogStage().getTitle());
            assertNotNull(dialog.getDialogStage().getScene());
        });
    }

    @Test
    void testShow() throws TimeoutException {
        FxToolkit.runLaterAndWait(() -> {
            dialog.show();
            assertTrue(dialog.getDialogStage().isShowing());
        });
    }
}