SET check_function_bodies = false;
CREATE TABLE food(
  "foodID" integer NOT NULL,
  "name" text NOT NULL,
  price float4 NOT NULL,
  "foodType" text NOT NULL,
  PRIMARY KEY("foodID")
);

CREATE TABLE "foodTicket"(
  amount integer NOT NULL,
  "ticketID" integer NOT NULL,
  "foodID" integer,
  FOREIGN KEY ("foodID") REFERENCES food("foodID")
  FOREIGN KEY ("ticketID") REFERENCES ticket("ticketID")
);

CREATE TABLE "foodIngredient"(
  "foodID" integer NOT NULL,
  "ingredientID" integer NOT NULL,
  amount integer NOT NULL,
  FOREIGN KEY ("foodID") REFERENCES food("foodID")
  FOREIGN KEY ("ingredientID") REFERENCES ingredient("ingredientID")
  
);

CREATE TABLE ingredient(
  "ingredientID" integer NOT NULL,
  "name"  TEXT NOT NULL,
  stock integer,
  PRIMARY KEY("ingredientID")
);

CREATE TABLE ticket(
  "ticketID" integer NOT NULL,
  "timeOrdered" timestamp NOT NULL,
  "totalCost" float,
  payment text,
  PRIMARY KEY("ticketID")
);
<<<<<<< HEAD
   
=======

ALTER TABLE "foodIngredient"
  ADD CONSTRAINT "foodIngredient_foodID_fkey"
    FOREIGN KEY ("foodID") REFERENCES food ("foodID");

ALTER TABLE ingredient
  ADD CONSTRAINT "ingredient_ingredientID_fkey"
    FOREIGN KEY ("ingredientID") REFERENCES "foodIngredient" ("ingredientID");

/*ALTER TABLE ticket
  ADD CONSTRAINT ticket_id_fkey
    FOREIGN KEY (id) REFERENCES "foodTicket" ("ticketID");

ALTER TABLE food
  ADD CONSTRAINT "food_foodID_fkey"
    FOREIGN KEY ("foodID") REFERENCES "foodTicket" ("foodID");*/
>>>>>>> 14403fef1ff3da5eb1f7bb735917e50189458d68
