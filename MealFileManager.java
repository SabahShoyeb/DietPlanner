package file;

import java.io.*;

public class MealFileManager {

    private File file;

    public MealFileManager(String filename) { 
        file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMealPlan(String mealPlan) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(mealPlan + "\n-----------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMealHistory() {
        StringBuilder history = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}
