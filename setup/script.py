import random

itemList = ["Ketchup",
            "Mayo",
            "Ranch",
            "Gig em Sauce",
            "Buffalo Sauce",
            "Honey Mustard",
            "Spicy Ranch",
            "Pickles",
            "Patties",
            "Chicken Tenders",
            "Fries",
            "Bun",
            "Lettuce",
            "Tomato",
            "Onion",
            "Vanilla Ice Cream",
            "Chocolate Ice Cream",
            "Strawberry Ice Cream",
            "Brownie",
            "Bacon",
            "Pepper Jack Cheese",
            "Texas Toast",
            "Black Bean Patty",
            "Kettle Chips",
            "Gravy",
            "Onion Rings",
            "Tater Tots",
            "Cold Brew",
            "Coffee Grounds",
            "Cheese",
            "Sesame Buns",
            "Cookie Dough",
            "Flour",
            "Jalapenos",
            "Pepsi Syrup",
            "Starry Syrup",
            "Gatorade Syrup",
            "Dr Pepper Syrup",
            "Diet Pepsi Syrup",
            "Chicken Patty",
            "Potatoes"
            ]

menuFooditems = ["Hamburger",
                 "Cheeseburger",
                 "Chicken Sandwich",
                 "5 Chicken Tenders Box",
                 "3 Chicken Tenders Box",
                 "Salad",
                 "Soda",
                 "Icecream",
                 "Icecream Sundae",
                 "Cookie",
                 "Onion Rings",
                 "Fries"]

payOptions = ["Dining Dollars",
              "Meal Swipes",
              "Card",
              "Cash"]

foodTypes = ["Burger", 
             "Baskets", 
             "Desert", 
             "Sides"]

foodIngredients = {
    "Hamburger": {"Bun": 2, "Patties": 1, "Lettuce": 1, "Tomato": 1, "Onion": 1}
    "Cheeseburger": {"Bun": 2, "Patties": 1, "Lettuce": 1, "Tomato": 1, "Onion": 1, "Cheese": 1}
    # ingredients for other food items to be added later
}

with open("fill.sql", "w") as fd:
    # fd.write("CREATE TABLE db("
    #          "Name TEXT PRIMARY KEY,"
    #          "Stock INT);\n")

    for i in range(len(itemList)):
        
        fd.write(f"INSERT INTO ingredient (\"ingredientID\", \"name\", stock)\nVALUES ({i+1}, '{itemList[i]}', {random.randint(0, 150)});\n")

    # filling tickets for 104 weeks (2 years)
    orderID = 1
    foodID = 1
    ingredientIDMap = {item: i+1 for i, item in enumerate(itemList)}
    for item in range(40):
                fd.write(f"INSERT INTO food (\"foodID\", name, price, \"foodType\")\nVALUES ({foodID}, 'Hamburger', 11.99, 'Burger');\n")

                for ingredient, amountRequired in foodIngredients['Hamburger'].items():
                    ingredientID = ingredientIDMap[ingredient]
                    fd.write(f"INSERT INTO foodIngredient (\"foodID\", \"ingredientID\", amount)\nVALUES ({foodID}, {ingredientID}, {amountRequired});\n")
                
                foodID += 1
    for day in range(728):
        daysOrders = random.randint(1, 15)
        
        for orderNum in range(daysOrders):
            # hours 10am - 8pm or 10 hours
            interval = 10 + (10/daysOrders)*orderNum
            fd.write(f"INSERT INTO ticket (\"ticketID\", \"timeOrdered\", \"totalCost\", payment)\nVALUES ({orderID}, date (LOCALTIMESTAMP) - {728 - day} + interval '{interval} hour', 20, '{payOptions[random.randint(0,3)]}');")
            numItems = random.randint(1, 12)
            
            for item in range(numItems):
                foodID = random.randint(0,40)
                fd.write(f"INSERT INTO foodticket (amount, \"ticketID\", \"foodID\")\nVALUES (1, {orderID}, {foodID});\n")
            
            orderID += 1



    # fd.write(f"INSERT INTO food (\"foodID\", name, price, \"foodType\")\nVALUES (69, 'Hamburger', 11.99, 'Burger');\n")
    
