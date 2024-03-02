package gui.database;

import java.util.*;
import java.sql.*;

//this class interacts with the database to get all of the information for what is currently on the menu
public class Menu { 
    
    Connection conn = null;
    String database_name = "csce331_902_01_db";
    String database_user = "csce331_902_01_user";
    String database_password = "EPICCSCEPROJECT";
    String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);

    //tgus function adds an item to the current menu
    public void addFood(String FoodName, String FoodCatagory, Double Price, Vector<String> Recipe){
        
        try {
            //estabilished connection to the database and finds the highest food ID so that a new ID can be created that is one higher to avoid conflict
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT MAX(\"foodID\") AS highest_id FROM food";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int highestId = 0; 
            if (rs.next()) {
                highestId = rs.getInt("highest_id");
            }

            rs.close();
            stmt.close();
            int newId = highestId +1;

            //insert into food table the new item with the new food id price catagory and sets that it is currently on the menu
            String insertQuery = "INSERT INTO food (\"foodID\", name, price, \"foodType\",onmenu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            pstmt.setInt(1, newId); 
            pstmt.setString(2, FoodName); 
            pstmt.setDouble(3, Price); 
            pstmt.setString(4, FoodCatagory); 
            pstmt.setInt(5, 1); 

            pstmt.executeUpdate();
            pstmt.close();
           

            //goes through each ingredient in the recipe and adds the relationship between the food and the item in the database
            for(int i =0; i < Recipe.size();i++){
                conn = DriverManager.getConnection(database_url, database_user, database_password);
                //checks if the ingredient exists if it does gives the id if not creates the ingredient
                int ingredientId = findOrCreateIngredient(Recipe.get(i));
                String ingredientQuery = "INSERT INTO foodingredient (\"foodID\", \"ingredientID\", \"amount\") VALUES (?, ?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(ingredientQuery);

                pstmt2.setInt(1, newId); //set the ingredient id
                pstmt2.setInt(2, ingredientId); // Set the food id
                pstmt2.setInt(3, 1); // Set the amount
                

                pstmt2.executeUpdate();
                pstmt2.close();
                
            }

            


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //finds the ingredient ID if the ingredient dosent exist returns -1
    public int getIngredientID(String ingredientName){
        int ingredientId = -1;
        try {
            //establshes connection to database and asks if their is an ID for the food name
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
            e.printStackTrace();
        }
        return ingredientId;
    }

    //returns the food ID for the food if it dosent exist returns -1
    public int getFoodID(String foodName) {
        int foodId = -1;
        try {
            //estabilished connection to database and asks if the food ID exists
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"foodID\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            //parses the return statment for the food id
            if (rs.next()) {
                foodId = rs.getInt("foodID");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodId;
    }

    //returns the name from food ID
    public String getFoodNameFromFoodID(int foodID) {
        String foodId = "";
        try {
            //estabilished connection to database and asks if the food ID exists
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT name FROM food WHERE \"foodID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, foodID);
            ResultSet rs = pstmt.executeQuery();
            //parses the return statment for the food id
            if (rs.next()) {
                foodId = rs.getString("name");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodId;
    }

    //gives the ingredient name from the ingredient ID and if it dosent returns an empty string
    public String getIngredientNameFromID(int IngredientId) {
        String foodName = "";
        try {
            //establishes a connection to the database and asks if the ingredient exists
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT name FROM ingredient WHERE \"ingredientID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, IngredientId);
            ResultSet rs = pstmt.executeQuery();
            //parses the return statment for the name
            if (rs.next()) {
                foodName = rs.getString("name");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodName;
    }

    //get price of an order from ticketID if it dosent exist return 0.0
    public Double getPriceFromTicketID(int ticketID) {
        Double ticketPrice = 0.0;
        try {
            //establishes a connection to the database and asks if the ingredient exists
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"totalCost\" FROM ticket WHERE \"ticketID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ticketID);
            ResultSet rs = pstmt.executeQuery();
            //parses the return statment for the name
            if (rs.next()) {
                ticketPrice = rs.getDouble("totalCost");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketPrice;
    }

    //get Time of an order from ticketID if it dosent exist return time 0
    public Timestamp getTimeFromTicketID(int ticketID) {
        Timestamp ticketDate = new Timestamp(0);
        try {
            //establishes a connection to the database and asks if the ingredient exists
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"timeOrdered\" FROM ticket WHERE \"ticketID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ticketID);
            ResultSet rs = pstmt.executeQuery();
            //parses the return statment for the name
            if (rs.next()) {
                ticketDate = rs.getTimestamp("timeOrdered");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketDate;
    }

    //finds the ingredient id and if it dosent exist creates a new ingredient with the name
    private int findOrCreateIngredient(String ingredientName){
        // Check if the ingredient already exists with that name
        int ingredientId = getIngredientID(ingredientName);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            
            //if the ingredient already exists return the ingredient ID
            if (ingredientId != -1) {
                return ingredientId;
            }

            //if the ingredient dosent exist finds the highest ID to avoid conflict
            String sql = "SELECT MAX(\"ingredientID\") AS highest_id FROM ingredient";
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery(sql);

            int highestId = 0; 
            if (rs2.next()) {
                highestId = rs2.getInt("highest_id");
            }

            rs2.close();
            stmt.close();
            int newId = highestId +1;

            // create the new ingredient with the new ID and the ingredient name
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
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ingredientId;
    }


    //returns the catagory of a food based on the food name returns an empty string if the food dosent exist
    public String getFoodCatagory(String foodName){
        String foodCatagory = "";
        try {
            //estabilishes a connection and asks what the type is of an item on the menu with the matching name
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"foodType\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            //Retrives the output from the database
            if (rs.next()) {
                foodCatagory = rs.getString("foodType");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodCatagory;
    }

    //gives a vector of each ingredient that is a part of the food name
    public Vector<String> getRecipe(String foodName){
        //finds the food ID from the food name
        int FoodID = getFoodID(foodName);
        Vector<String> Recipe = new Vector<String>(1);
        try {
            //conects to the food ingredient table and asks for all things with the foodID related to it
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"ingredientID\" FROM foodIngredient WHERE \"foodID\" = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, FoodID);
            ResultSet rs = pstmt.executeQuery();
            //creates a vector with all of the ingredient ID's
            Vector<Integer> tempIngredientsID = new Vector<Integer>(1);
            while (rs.next()) {
                int ingredientID = rs.getInt("ingredientID");
                tempIngredientsID.add(ingredientID);
            }
            
            //converts ingredient ID's to ingredient names
            
            for(int i = 0; i < tempIngredientsID.size();i++){
                String ingredientName = getIngredientNameFromID(tempIngredientsID.get(i));
                Recipe.add(ingredientName);
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(Recipe);
    }

    //gives all foods of a specific food type
    public Vector<String> getFoodFromFoodType(String Catagory){
        
        Vector<String> tempFoodNames = new Vector<String>(1);
        try {
            //gives one of each food from a specific types of food
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT DISTINCT \"name\" FROM food WHERE \"foodType\" = ? and onmenu = 1";
            PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, Catagory);
            ResultSet rs = stmt.executeQuery();
            

            
            //adds all of the foods to a vector
            while (rs.next()) {
                String foodName = rs.getString("name");
                tempFoodNames.add(foodName);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(tempFoodNames);
    }
    
    //gives all food types that are on the menu
    public Vector<String> getFoodTypes(){
        // SQL query to retrieve distinct food types for food that are on the menu
        String sql = "SELECT DISTINCT \"foodType\" FROM food WHERE onMenu = 1";
        Statement stmt;
        Vector<String> tempFoodTypes = new Vector<String>(1);
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // Process the result set and add food types to the Vector
            while (rs.next()) {
                String foodType = rs.getString("foodType");
                tempFoodTypes.add(foodType);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(tempFoodTypes);
    }

    //gives the price of a food returns 0.0 if the item dosent exist
    public Double getPrice(String foodName){
        Double foodPrice = 0.0;
        try {
            //gets the price of a food that matches the name
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "SELECT \"price\" FROM food WHERE name = ? and onmenu = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, foodName);
            ResultSet rs = pstmt.executeQuery();
            //process the results and set the variable
            if (rs.next()) {
                foodPrice = rs.getDouble("price");
            }
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodPrice;
    }

    //returns all orders of a specific year and month
    public Vector<Integer> getOrdersFromYearAndMonth(int year, int month){
        
        Vector<Integer> tempOrderIDs = new Vector<Integer>(1);
        try {
            //gives all orders for the specified month in the specified year
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "select * from ticket where DATE_Part(\'month\',\"timeOrdered\") = ? and date_part(\'year\',\"timeOrdered\") = ?";
            PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            

            
            //adds all of the orders into a vector
            while (rs.next()) {
                int orderId = rs.getInt("ticketID");
                tempOrderIDs.add(orderId);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(tempOrderIDs);
    }

    //get all foodnames of a specific ticket ID
    public Vector<String> getFoodFromTicketID(int ticketID){
        
        Vector<String> foodNames = new Vector<String>(1);
        try {
            //gives one of each food from a specific types of food
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sql = "select * from foodticket where \"ticketID\" = ?";
            PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, ticketID);
            ResultSet rs = stmt.executeQuery();
            

            
            //adds all of the food ids and convert them to names and then add them to the vector
            while (rs.next()) {
                Integer tempFoodID = rs.getInt("name");
                foodNames.add(getFoodNameFromFoodID(tempFoodID));
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(foodNames);
    }

    //changes the price of a food
    public void changePrice(String FoodName, Double Price){
        try (Connection conn = DriverManager.getConnection(database_url, database_user, database_password)) {
            // SQL query to update the price of the food item
            String sql = "UPDATE food SET price = ? WHERE \"foodID\" = ?";
            
            // Create a PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int foodID = getFoodID(FoodName);
            // Set the parameters
            pstmt.setDouble(1, Price);
            pstmt.setInt(2, foodID);
            
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            
            //checks if changes occured
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


    //removes a food from the menu
    public void removeFood(String FoodName){
        try (Connection conn = DriverManager.getConnection(database_url, database_user, database_password)) {
            // SQL query to change the on menu value to show that it is not on the menu
            String sql = "UPDATE food SET \"onmenu\" = 0 WHERE \"foodID\" = ? and onmenu = 1";
            
            // Create a PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int foodID = getFoodID(FoodName);
            // Set the parameters
            pstmt.setInt(1, foodID);
            
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            
            //checks if it was updated
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
    public void generateBasicMenu() {
        addBurgers();
        addBaskets();
        addSandwiches();
        addSides();
        addSauces();
        addBeverages();
    }

    // Helper methods to add different categories of items to the menu
    public void addBurgers() {
        addFood("Rev's Burger", "Burgers", 5.59, new Vector<>(Arrays.asList(
            "Burger Patty",
            "Cheese",
            "Gig-em sauce",
            "Buns"
        )));

        addFood("Double stack cheeseburger", "Burgers", 8.79, new Vector<>(Arrays.asList(
                "Burger Patty",
                "Burger Patty",
                "Cheese",
                "Cheese",
                "Gigem sauce",
                "Pickles",
                "Buns"
        )));

        addFood("Classic Burger", "Burgers", 5.47, new Vector<>(Arrays.asList(
                "Buns",
                "Burger Patty",
                "Lettuce",
                "Tomato",
                "Pickle",
                "Onions"
        )));

        addFood("Bacon Cheese Burger", "Burgers", 6.99, new Vector<>(Arrays.asList(
                "Burger Patty",
                "Cheese",
                "Bacon",
                "Buns"
        )));
    }

    public void addBaskets() {
        addFood("Three Tender Basket", "Baskets", 6.79, new Vector<>(Arrays.asList(
                "Chicken tender",
                "Chicken tender",
                "Chicken tender",
                "French fries",
                "Texas toast",
                "Gravy"
        )));

        addFood("Four Steak Finger Basket", "Baskets", 6.99, new Vector<>(Arrays.asList(
            "4x Steak finger",
            "French Fries",
            "Texas Toast",
            "Gravy"
        )));
    }

    public void addSandwiches() {
        addFood("Gig 'Em Patty Melt", "Sandwiches", 6.29, new Vector<>(Arrays.asList(
                "Burger patty",
                "Texas toast",
                "Gigem sauce",
                "Onions",
                "Cheese"
        )));

        addFood("Spicy ranch chicken sandwich", "Sandwiches", 6.99, new Vector<>(Arrays.asList(
            "Chicken tender",
            "Chicken tender",
            "Bun",
            "Spicy ranch sauce",
            "Cheese"
        )));

        addFood("Classic Chicken Sandwich", "Sandwiches", 5.79, new Vector<>(Arrays.asList(
            "Chicken tender",
            "Chicken tender",
            "Bun",
            "Lettuce",
            "Tomato",
            "Pickle",
            "Onion"
        )));

        addFood("Grilled Cheese", "Sandwiches", 3.49, new Vector<>(Arrays.asList(
                "Cheese",
                "Texas toast"
        )));
    }

    public void addSides() {
        addFood("Tater tots", "Sides", 1.00, new Vector<>());
        addFood("Onion Rings", "Sides", 1.00, new Vector<>());
        addFood("Kettle Chips", "Sides", 1.00, new Vector<>());
        addFood("Fries", "Sides", 1.00, new Vector<>());
    }

    public void addSauces() {
       addFood("Gigem sauce", "Sauces", 0.50, new Vector<>());
        addFood("Buffalo", "Sauces", 0.50, new Vector<>());
        addFood("Ranch", "Sauces", 0.50, new Vector<>());
        addFood("Spicy ranch", "Sauces", 0.50, new Vector<>());
        addFood("BBQ", "Sauces", 0.50, new Vector<>());
        addFood("Honey Mustard", "Sauces", 0.50, new Vector<>());
    }

    public void addBeverages() {
        addFood("Fountain Drink", "Beverages", 1.50, new Vector<>());
        addFood("Shake", "Beverages", 3.00, new Vector<>());
    }
}