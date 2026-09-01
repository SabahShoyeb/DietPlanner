package entity;

public abstract class Meal {
    private String name;
    private int calories;

    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public abstract String getType();

    public String toString() {
        return getName() + " (" + getCalories() + " cal)";
    }
}
