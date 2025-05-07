package com.smarthabittracker.ui;

import com.smarthabittracker.model.Habit;
import com.smarthabittracker.services.HabitService;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class HabitTrackerUITest {

    private HabitTrackerUI app;
    private HabitService habitService;
    private Stage stage;

    @Start
    void start(Stage stage) {
        this.stage = stage;
        habitService = mock(HabitService.class);
        app = new HabitTrackerUI() {
            @Override
            protected HabitService getHabitService() {
                return habitService;
            }
        };
        app.start(stage);
    }

    @BeforeEach
    void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    void testLoadHabits_Success(FxRobot robot) throws IOException, TimeoutException {
        Habit habit = new Habit("Test Habit", "Description");
        when(habitService.getAllHabits()).thenReturn(List.of(habit));

        FxToolkit.runLaterAndWait(() -> app.loadHabits());
        assertEquals(1, app.getHabitTable().getItems().size());
    }

    @Test
    void testUpdateStats(FxRobot robot) throws TimeoutException {
        Habit habit1 = new Habit("Habit1", "Desc1");
        habit1.setStreak(2);
        habit1.setTotalCompletions(3);
        habit1.setLastCompletedDate(LocalDate.now());
        Habit habit2 = new Habit("Habit2", "Desc2");
        habit2.setStreak(4);
        habit2.setTotalCompletions(5);

        FxToolkit.runLaterAndWait(() -> app.updateStats(List.of(habit1, habit2)));
        assertEquals("Total Habits: 2", app.getTotalHabitsLabel().getText());
        assertEquals("Completed Today: 1", app.getCompletedHabitsLabel().getText());
        assertEquals("Average Streak: 3.0", app.getAverageStreakLabel().getText());
        assertEquals("Total Completions: 8", app.getTotalCompletionsLabel().getText());
    }
}