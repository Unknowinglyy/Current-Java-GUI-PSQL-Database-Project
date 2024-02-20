import random

itemList = ["Ketchup",
            "Mayo",
            "Ranch",
            "Gig’ em Sauce",
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
            ]

with open("fill.sql", "w") as fd:
    fd.write("CREATE TABLE db("
             "Name TEXT PRIMARY KEY,"
             "Stock INT);\n")
    for item in itemList:
        fd.write(f"INSERT INTO db (Name, Stock)\nVALUES ('{item}', {random.randrange(0,100)});")