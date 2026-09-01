package entity;

public class NonVegMeal extends Meal {

    public NonVegMeal(String name, int calories) {
        super(name, calories);
    }
    public String getType() {
        return "Non-Veg";
    }
}

