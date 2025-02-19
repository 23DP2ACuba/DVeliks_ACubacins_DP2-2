public class Habit {
    private String name;
    private String category;
    private int streak;
    private boolean completedToday;

    public Habit(String name, String category) {
        this.name = name;
        this.category = category;
        this.streak = 0;
        this.completedToday = false;
    }

    public void markCompleted() {
        if (!completedToday) {
            streak++;
            completedToday = true;
        }
    }
    
    public String toCSV() {
        return name + "," + category + "," + streak + "," + completedToday;
    }
    
    public static Habit fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) return null;
        Habit habit = new Habit(parts[0], parts[1]);
        habit.streak = Integer.parseInt(parts[2]);
        habit.completedToday = Boolean.parseBoolean(parts[3]);
        return habit;
    }
    
    public String getName() { return name; }
    public int getStreak() { return streak; }
}