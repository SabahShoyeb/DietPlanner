package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import entity.*;
import file.MealFileManager;

public class DietPlannerFrame {

    static Meal[][] dietMeals = {
        {
            new VeganMeal("Vegan smoothie bowl", 350),
            new VeganMeal("Tofu scramble", 300),
            new VeganMeal("Lentil soup", 250),
            new VeganMeal("Quinoa salad", 400),
            new VeganMeal("Chickpea curry", 450)
        },
        {
            new VegetarianMeal("Paneer curry with rice", 500),
            new VegetarianMeal("Vegetable lasagna", 450),
            new VegetarianMeal("Cheese sandwich", 350),
            new VegetarianMeal("Greek salad", 300),
            new VegetarianMeal("Mushroom pasta", 400)
        },
        {
            new NonVegMeal("Grilled chicken breast", 400),
            new NonVegMeal("Egg omelette with toast", 350),
            new NonVegMeal("Baked salmon with veggies", 500),
            new NonVegMeal("Beef stir-fry", 550),
            new NonVegMeal("Tuna sandwich", 450)
        }
    };

    static String[] dietTypes = {"Vegan", "Vegetarian", "Non-Veg"};
    static String lastMealPlan = "";
    static MealFileManager manager = new MealFileManager("meal_history.txt");

    public static void createAndShowGUI() {

        JFrame frame = new JFrame("Meal Planner");
        frame.setSize(500, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bgColor = new Color(20, 120, 135);
        Font labelFont = new Font("Cambria", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);
        panel.setBounds(0, 0, 500, 700);
        frame.add(panel);

        JLabel titleLabel = new JLabel("Simple Diet Planner", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBounds(100, 10, 300, 30);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 40, 100, 25);
        nameLabel.setFont(labelFont);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(160, 40, 200, 25);
        nameField.setFont(fieldFont);
        panel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 80, 100, 25);
        ageLabel.setFont(labelFont);
        panel.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(160, 80, 200, 25);
        ageField.setFont(fieldFont);
        panel.add(ageField);

        JLabel weightLabel = new JLabel("Weight (kg):");
        weightLabel.setBounds(50, 120, 100, 25);
        weightLabel.setFont(labelFont);
        panel.add(weightLabel);

        JTextField weightField = new JTextField();
        weightField.setBounds(160, 120, 200, 25);
        weightField.setFont(fieldFont);
        panel.add(weightField);

        JLabel heightLabel = new JLabel("Height (cm):");
        heightLabel.setBounds(50, 160, 100, 25);
        heightLabel.setFont(labelFont);
        panel.add(heightLabel);

        JTextField heightField = new JTextField();
        heightField.setBounds(160, 160, 200, 25);
        heightField.setFont(fieldFont);
        panel.add(heightField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 200, 100, 25);
        genderLabel.setFont(labelFont);
        panel.add(genderLabel);

        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setBounds(160, 200, 200, 25);
        panel.add(genderBox);

        JLabel dietLabel = new JLabel("Diet Type:");
        dietLabel.setBounds(50, 240, 100, 25);
        dietLabel.setFont(labelFont);
        panel.add(dietLabel);

        JComboBox<String> dietBox = new JComboBox<>(dietTypes);
        dietBox.setBounds(160, 240, 200, 25);
        panel.add(dietBox);

        JLabel mealCountLabel = new JLabel("Meals per day:");
        mealCountLabel.setBounds(50, 280, 120, 25);
        mealCountLabel.setFont(labelFont);
        panel.add(mealCountLabel);

        JComboBox<String> mealCountBox = new JComboBox<>(new String[]{"2", "3", "4"});
        mealCountBox.setBounds(160, 280, 200, 25);
        panel.add(mealCountBox);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(50, 320, 380, 200);
        panel.add(scrollPane);

        JButton generate = new JButton("Generate Meal Plan");
        generate.setBounds(150, 540, 200, 30);
        panel.add(generate);

        JButton history = new JButton("History");
        history.setBounds(150, 580, 200, 30);
        panel.add(history);

        
        generate.addActionListener(new ActionListener() {
   
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String ageText = ageField.getText();
                String weightText = weightField.getText();
                String heightText = heightField.getText();

                if (name.isEmpty() || ageText.isEmpty() || weightText.isEmpty() || heightText.isEmpty()) {
                    output.setText("Please fill in all fields.");
                    return;
                }

                try {
                    int age = Integer.parseInt(ageText);
                    double weight = Double.parseDouble(weightText);
                    double height = Double.parseDouble(heightText);
                    double heightM = height / 100.0;

                    double bmi = weight / (heightM * heightM);
                    String gender = (String) genderBox.getSelectedItem();

                    int baseCalories;
                    switch (gender) {
                        case "Male": baseCalories = 2200; break;
                        case "Female": baseCalories = 1800; break;
                        default: baseCalories = 2000;
                    }

                    String goal;
                    int targetCalories;
                    if (bmi < 18.5) {
                        goal = "Weight Gain";
                        targetCalories = baseCalories + 500;
                    } else if (bmi >= 25) {
                        goal = "Weight Loss";
                        targetCalories = baseCalories - 500;
                    } else {
                        goal = "Maintain";
                        targetCalories = baseCalories;
                    }

                    int dietIndex = dietBox.getSelectedIndex();
                    Meal[] options = dietMeals[dietIndex];
                    int mealCount = Integer.parseInt((String) mealCountBox.getSelectedItem());

                    StringBuilder result = new StringBuilder("Meal Plan for " + name + " (" + age + " yrs, " + weight + " kg, " + height + " cm)\n");
                    result.append("Gender: ").append(gender).append(" | BMI: ").append(String.format("%.2f", bmi)).append("\n");
                    result.append("Diet: ").append(dietTypes[dietIndex]).append(" | Inferred Goal: ").append(goal).append("\n\n");

                    Random rand = new Random();
                    boolean[] used = new boolean[options.length];
                    int total = 0;
                    for (int i = 0; i < mealCount; i++) {
                        int index;
                        do {
                            index = rand.nextInt(options.length);
                        } while (used[index]);
                        used[index] = true;
                        Meal selected = options[index];
                        result.append("Meal ").append(i + 1).append(": ").append(selected).append("\n");
                        total += selected.getCalories();
                    }

                    result.append("\nTotal Calories: ").append(total).append(" cal\n");
                    result.append("Target Calories: ").append(targetCalories).append(" cal\n");

                    if (total < targetCalories - 200)
                        result.append("Advice: You might need to increase portions or add snacks.");
                    else if (total > targetCalories + 200)
                        result.append("Advice: Consider smaller portions or lighter options.");
                    else
                        result.append("Great! Your meal plan matches your goal.");

                    output.setText(result.toString());
                    lastMealPlan = result.toString();

                } catch (NumberFormatException ex) {
                    output.setText("Please enter valid numeric values.");
                }
            }
        });

        
        history.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (lastMealPlan.isEmpty()) {
                    output.setText("No meal plan to save. Please generate one first.");
                } else {
                    manager.saveMealPlan(lastMealPlan);
                    String fullHistory = manager.readMealHistory();
                    output.setText("Meal plan saved to history!\n\nFull History:\n" + fullHistory);
                }
            }
        });

        frame.setVisible(true);
    }
}

