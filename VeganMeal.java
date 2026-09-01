package entity;

public class VeganMeal extends Meal{
    public VeganMeal(String name, int calories) {
        super(name, calories);
    }

    public String getType() {
        return "Vegan";
    }
}

