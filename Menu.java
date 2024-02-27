import java.util.*;

public class Menu { 
    //types of food used to find index for Food Items
    Vector<String> FoodTypes = new Vector<String>(1);
    //Specific Foods sorted by Food types Used to find index for Ingredients
    Vector<Vector<String>> FoodItems = new Vector<Vector<String>>(1);
    //Ingredients for specific foods sorted by food type then food
    Vector<Vector<Vector<String>>> Ingredients = new Vector<Vector<Vector<String>>>(1);

    public void AddFood(String FoodName, String FoodCatagory, Double Price, Vector<String> Recipe){
        //checks if Food type already exists
        if(FoodTypes.indexOf(FoodCatagory) == -1){
            FoodTypes.addElement(FoodCatagory);
            Vector<String> blank = new Vector<String>();
            FoodItems.addElement(blank);
            Vector<Vector<String>> blanker = new Vector<Vector<String>>();
            Ingredients.addElement(blanker);
        }

        //finds the location
        int typeIndex = FoodTypes.indexOf(FoodCatagory);
        //adds the food to the list of foods under the catagory
        FoodItems.get(typeIndex).addElement(FoodName);
        //adds the price to the ingredients
        Recipe.add(0,Double.toString(Price));
        //adds the ingredients for the food
        Ingredients.get(typeIndex).addElement(Recipe);
    }

    //returns the catagorie of a food
    public String GetFoodCatagory(String FoodName){
        //goes through each catagory and checks if the food is in it
        for(int i = 0; i < FoodItems.size();i++){
            //if it find the item of the name return the catagory
            if(FoodItems.get(i).indexOf(FoodName) != -1){
                String FoodType = FoodTypes.get(i);
                return(FoodType);
            }
        }
        return("");
    }

    public Vector<String> GetRecipe(String FoodName){
        //finds the food in ingredients
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        Vector<String> Recipe = Ingredients.get(CatIndex).get(foodIndex);
        //gets rid of the price
        Recipe.remove(0);
        return(Recipe);
    }

    //all foods of a food type
    public Vector<String> GetFoodFromFoodType(String Catagory){
        //finds the food in ingredients
        int CatIndex = FoodTypes.indexOf(Catagory);
        return(FoodItems.get(CatIndex));
    }
    
    //gives all food types
    public Vector<String> GetFoodTypes(){
        return(FoodTypes);
    }
    public Double GetPrice(String FoodName){
        //finds the food
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        Vector<String> Recipe = Ingredients.get(CatIndex).get(foodIndex);
        //gets the price
        Double Price = Double.parseDouble(Recipe.get(0));
        return(Price);
    }

    //changes the price of a food
    public void ChangePrice(String FoodName, Double Price){
        //finds the food
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        //changes the food price to the new Price
        Ingredients.get(CatIndex).get(foodIndex).set(0,Double.toString(Price));
    }

    //removes a food
    public void RemoveFood(String FoodName){
        //finds teh food
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        //removes the food
        Ingredients.get(CatIndex).remove(foodIndex);
        FoodItems.remove(foodIndex);
    }

    //removes a food type
    public void RemoveFoodType(String Catagory){
        int CatIndex = FoodTypes.indexOf(Catagory);
        FoodTypes.remove(CatIndex);
        FoodItems.remove(CatIndex);
        Ingredients.remove(CatIndex);
    }

    //adds a the basic rev menu
    public void GenerateBasicMenu(){
        addBurgers();
        addBaskets();
        addSandwiches();
        addSides();
        addSauces();
        addBeverages();
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

    public  void addBaskets() {
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
