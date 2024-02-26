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
        }

        //finds the location
        int typeIndex = FoodTypes.indexOf(FoodCatagory);
        //adds the food to the list of foods under the catagory
        FoodItems.get(typeIndex).addElement(FoodName);
        //adds the price to the ingredients
        Recipe.addFirst(Double.toString(Price));
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
        Recipe.removeFirst();
        return(Recipe);
    }
    
    public Double GetPrice(String FoodName){
        //finds the food
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        Vector<String> Recipe = Ingredients.get(CatIndex).get(foodIndex);
        //gets the price
        Double Price = Double.parseDouble(Recipe.getFirst());
        return(Price);
    }

    public void ChangePrice(String FoodName, Double Price){
        //finds the food
        String Catagory = GetFoodCatagory(FoodName);
        int CatIndex = FoodTypes.indexOf(Catagory);
        int foodIndex = FoodItems.get(CatIndex).indexOf(FoodName);
        //changes the food price to the new Price
        Ingredients.get(CatIndex).get(foodIndex).set(0,Double.toString(Price));
    }
}
