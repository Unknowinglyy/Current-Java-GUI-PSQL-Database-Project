package gui.database;

package gui.database;

import java.util.*;
import java.sql.*;

public class Menu { 
    
    Connection conn = null;
    String database_name = "csce331_902_01_db";
    String database_user = "csce331_902_01_user";
    String database_password = "EPICCSCEPROJECT";
    String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);

    //checked
    public void AddFood(String FoodName, String FoodCatagory, Double Price, Vector<String> Recipe){
        //checks if Food type already exists
        //"INSERT INTO food (\"foodID\", name, price, \"foodType\")\nVALUES ({foodID}, 'Hamburger', 11.99, 'Burger');\n"
        
        //connect to database
        
        //trys to add the food to order
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT MAX(\"foodID\") AS highest_id FROM food";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int highestId = 0; // Initialize with default value
            if (rs.next()) {
                highestId = rs.getInt("highest_id");
            }

            rs.close();
            stmt.close();
            int newId = highestId +1;
            String insertQuery = "INSERT INTO food (\"foodID\", name, price, \"foodType\",onmenu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            pstmt.setInt(1, newId); //set id
            pstmt.setString(2, FoodName); // Set the name
            pstmt.setDouble(3, Price); // Set the price
            pstmt.setString(4, FoodCatagory); //set catagory
            pstmt.setInt(5, 1); // Set onMenu

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            //goes through all ingredients and creates the relationship between food and the item
            for(int i =0; i < Recipe.size();i++){
                conn = DriverManager.getConnection(database_url, database_user, database_password);
                int ingredientId = findOrCreateIngredient(Recipe.get(i));
                String ingredientQuery = "INSERT INTO foodingredient (\"foodID\", \"ingredientID\", \"amount\") VALUES (?, ?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(ingredientQuery);

                pstmt2.setInt(1, newId); //set the ingredient id
                pstmt2.setInt(2, ingredientId); // Set the food id
                pstmt2.setInt(3, 1); // Set the amount
                

                pstmt2.executeUpdate();
                pstmt2.close();
                conn.close();
            }

            


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public int findIngredientId(String ingredientName){
        int ingredientId = -1;
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"ingredientID\" FROM ingredient WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ingredientName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ingredientId = rs.getInt("ingredientID");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ingredientId;
    }

    public int findFoodId(String foodName) {
        int foodId = -1;
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"foodID\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                foodId = rs.getInt("foodID");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return foodId;
    }

    public String getIngredientNameFromID(int IngredientId) {
        String foodName = "";
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT name FROM ingredient WHERE \"ingredientID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, IngredientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                foodName = rs.getString("name");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return foodName;
    }

    private int findOrCreateIngredient(String ingredientName){
        // Check if the ingredient already exists
        int ingredientId = findIngredientId(ingredientName);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            
            if (ingredientId != -1) {
                return ingredientId;
            }

            //if not finds the highest id
            String sql = "SELECT MAX(\"ingredientID\") AS highest_id FROM ingredient";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql);

            int highestId = 0; // Initialize with default value
            if (rs2.next()) {
                highestId = rs2.getInt("highest_id");
            }

            rs2.close();
            stmt.close();
            //gets 1 higher to avoid conflicts
            int newId = highestId +1;

            // If the ingredient doesn't exist, create it
            String sql2 = "INSERT INTO ingredient (\"ingredientID\",name,stock) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, newId);
            pstmt.setString(2,ingredientName);
            pstmt.setInt(3, 0);
            pstmt.executeUpdate();

            // Retrieve the auto-generated ID of the newly inserted ingredient
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                ingredientId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ingredientId;
    }


    //returns the catagorie of a food
    public String GetFoodCatagory(String foodName){
        String foodCatagory = "";
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"foodType\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                foodCatagory = rs.getString("foodType");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return foodCatagory;
    }

    public Vector<String> GetRecipe(String foodName){
        int FoodID = findFoodId(foodName);
        Vector<String> Recipe = new Vector<String>(1);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"ingredientID\" FROM foodIngredient WHERE \"foodID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, FoodID);
            ResultSet rs = pstmt.executeQuery();
            Vector<Integer> tempIngredientsID = new Vector<Integer>(1);
            while (rs.next()) {
                int ingredientID = rs.getInt("ingredientID");
                tempIngredientsID.add(ingredientID);
            }
            
            //converts int ingredients to names
            
            for(int i = 0; i < tempIngredientsID.size();i++){
                String ingredientName = getIngredientNameFromID(tempIngredientsID.get(i));
                Recipe.add(ingredientName);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return(Recipe);
    }

    //all foods of a food type
    public Vector<String> GetFoodFromFoodType(String Catagory){
        // SQL query to retrieve distinct food types where onMenu = 1
        // Create a statement
        
        Vector<String> tempFoodNames = new Vector<String>(1);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"name\" FROM food WHERE \"foodType\" = ? and onmenu = 1";
            PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, Catagory);
                // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();
            

            
            // Process the result set and add food types to the Vector
            while (rs.next()) {
                String foodName = rs.getString("name");
                tempFoodNames.add(foodName);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(tempFoodNames);
    }
    
    //gives all food types
    public Vector<String> GetFoodTypes(){
        // SQL query to retrieve distinct food types where onMenu = 1
        String sql = "SELECT DISTINCT \"foodType\" FROM food WHERE onMenu = 1";

        // Create a statement
        Statement stmt;
        Vector<String> tempFoodTypes = new Vector<String>(1);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            stmt = conn.createStatement();
                // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery(sql);

            
            // Process the result set and add food types to the Vector
            while (rs.next()) {
                String foodType = rs.getString("foodType");
                tempFoodTypes.add(foodType);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(tempFoodTypes);
    }
    public Double GetPrice(String foodName){
        Double foodPrice = 0.0;
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"price\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                foodPrice = rs.getDouble("price");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return foodPrice;
    }

    //changes the price of a food
    public void ChangePrice(String FoodName, Double Price){
        try (Connection conn = DriverManager.getConnection(database_url, database_user, database_password)) {
            // SQL query to update the price of the food item
            String sql = "UPDATE food SET price = ? WHERE \"foodID\" = ?";
            
            // Create a PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int foodID = findFoodId(FoodName);
            // Set the parameters
            pstmt.setDouble(1, Price);
            pstmt.setInt(2, foodID);
            
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Price for food item with foodID " + foodID + " updated successfully.");
            } else {
                System.out.println("No food item found with foodID " + foodID + ".");
            }

            // Close the statement and connection
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //removes a food
    public void RemoveFood(String FoodName){
        try (Connection conn = DriverManager.getConnection(database_url, database_user, database_password)) {
            // SQL query to update the price of the food item
            String sql = "UPDATE food SET \"onmenu\" = 0 WHERE \"foodID\" = ? and onmenu = 1";
            
            // Create a PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int foodID = findFoodId(FoodName);
            // Set the parameters
            pstmt.setInt(1, foodID);
            
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Price for food item with foodID " + foodID + " updated successfully.");
            } else {
                System.out.println("No food item found with foodID " + foodID + ".");
            }

            // Close the statement and connection
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    //adds a the basic rev menu
    public void GenerateBasicMenu() {
        //RemoveFood("Double stack cheeseburger");
        // addBurgers();
        // addBaskets();
        // addSandwiches();
        // addSides();
        // addSauces();
        // addBeverages();
    }

    // Helper methods to add different categories of items to the menu
    public void addBurgers() {
        AddFood("Rev's Burger", "Burgers", 5.59, new Vector<>(Arrays.asList(
            "Burger Patty",
            "Cheese",
            "Gig-em sauce",
            "Buns"
        )));

        AddFood("Double stack cheeseburger", "Burgers", 8.79, new Vector<>(Arrays.asList(
                "Burger Patty",
                "Burger Patty",
                "Cheese",
                "Cheese",
                "Gigem sauce",
                "Pickles",
                "Buns"
        )));

        AddFood("Classic Burger", "Burgers", 5.47, new Vector<>(Arrays.asList(
                "Buns",
                "Burger Patty",
                "Lettuce",
                "Tomato",
                "Pickle",
                "Onions"
        )));

        AddFood("Bacon Cheese Burger", "Burgers", 6.99, new Vector<>(Arrays.asList(
                "Burger Patty",
                "Cheese",
                "Bacon",
                "Buns"
        )));
    }

    public void addBaskets() {
        AddFood("Three Tender Basket", "Baskets", 6.79, new Vector<>(Arrays.asList(
                "Chicken tender",
                "Chicken tender",
                "Chicken tender",
                "French fries",
                "Texas toast",
                "Gravy"
        )));

        AddFood("Four Steak Finger Basket", "Baskets", 6.99, new Vector<>(Arrays.asList(
            "4x Steak finger",
            "French Fries",
            "Texas Toast",
            "Gravy"
        )));
    }

    public void addSandwiches() {
        AddFood("Gig 'Em Patty Melt", "Sandwiches", 6.29, new Vector<>(Arrays.asList(
                "Burger patty",
                "Texas toast",
                "Gigem sauce",
                "Onions",
                "Cheese"
        )));

        AddFood("Spicy ranch chicken sandwich", "Sandwiches", 6.99, new Vector<>(Arrays.asList(
            "Chicken tender",
            "Chicken tender",
            "Bun",
            "Spicy ranch sauce",
            "Cheese"
        )));

        AddFood("Classic Chicken Sandwich", "Sandwiches", 5.79, new Vector<>(Arrays.asList(
            "Chicken tender",
            "Chicken tender",
            "Bun",
            "Lettuce",
            "Tomato",
            "Pickle",
            "Onion"
        )));

        AddFood("Grilled Cheese", "Sandwiches", 3.49, new Vector<>(Arrays.asList(
                "Cheese",
                "Texas toast"
        )));
    }

    public void addSides() {
        AddFood("Tater tots", "Sides", 1.00, new Vector<>());
        AddFood("Onion Rings", "Sides", 1.00, new Vector<>());
        AddFood("Kettle Chips", "Sides", 1.00, new Vector<>());
        AddFood("Fries", "Sides", 1.00, new Vector<>());
    }

    public void addSauces() {
       AddFood("Gigem sauce", "Sauces", 0.50, new Vector<>());
        AddFood("Buffalo", "Sauces", 0.50, new Vector<>());
        AddFood("Ranch", "Sauces", 0.50, new Vector<>());
        AddFood("Spicy ranch", "Sauces", 0.50, new Vector<>());
        AddFood("BBQ", "Sauces", 0.50, new Vector<>());
        AddFood("Honey Mustard", "Sauces", 0.50, new Vector<>());
    }

    public void addBeverages() {
        AddFood("Fountain Drink", "Beverages", 1.50, new Vector<>());
        AddFood("Shake", "Beverages", 3.00, new Vector<>());
    }
}