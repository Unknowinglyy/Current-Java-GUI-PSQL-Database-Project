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

with open("fill.sql", "w") as fd:
    # fd.write("CREATE TABLE db("
    #          "Name TEXT PRIMARY KEY,"
    #          "Stock INT);\n")
    for i in range(len(itemList)):
        
        fd.write(f"INSERT INTO ingredient (\"ingredientID\", \"name\", stock)\nVALUES ({i+1}, '{itemList[i]}', {random.randint(0, 150)});\n")

    fd.write(f"INSERT INTO food (\"foodID\", name, price, \"foodType\")\nVALUES (69, 'Hamburger', 11.99, 'Burger');\n")
    