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

with open("fill.sql", "w") as fd:
    # fd.write("CREATE TABLE db("
    #          "Name TEXT PRIMARY KEY,"
    #          "Stock INT);\n")
    for i in range(len(itemList)):
        
        fd.write(f"INSERT INTO ingredient (\"ingredientID\", \"name\", stock)\nVALUES ({i+1}, '{itemList[i]}', {random.randint(0, 150)});\n")

    # filling tickets for 104 weeks (2 years)
    orderID = 1
    for day in range(728):
        daysOrders = random.randint(30, 50)
        
        for orderNum in range(daysOrders):
            # hours 10am - 8pm or 10 hours
            interval = 10 + (10/daysOrders)*orderNum
            fd.write(f"INSERT INTO ticket (\"ticketID\", \"timeOrdered\", \"totalCost\", payment)\n VALUES ({orderID}, date (LOCALTIMESTAMP) - {728 - day} + interval '{interval} hour', 20, 'dining dollars');")
            orderID = orderID+1

    fd.write(f"INSERT INTO food (\"foodID\", name, price, \"foodType\")\nVALUES (69, 'Hamburger', 11.99, 'Burger');\n")
    