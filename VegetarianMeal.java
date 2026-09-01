package entity;

public class VegetarianMeal extends Meal {
    public VegetarianMeal(String name, int calories) {
        super(name, calories);
    }

    public String getType() {
        return "Vegetarian";
    }
}
