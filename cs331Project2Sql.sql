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
  CONSTRAINT ticket_fkey UNIQUE("ticketID"),
  CONSTRAINT food_fkey UNIQUE("foodID")
);

CREATE TABLE "foodIngredient"(
  "foodID" integer NOT NULL,
  "ingredientID" integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  CONSTRAINT "foodIngredient_ingredientID_key" UNIQUE("ingredientID"),
  CONSTRAINT "foodIngredient_key1" UNIQUE("foodID")
);

CREATE TABLE ingredient(
  "ingredientID" integer NOT NULL,
  "name"  TEXT NOT NULL,
  stock integer,
  PRIMARY KEY("ingredientID")
);

CREATE TABLE ticket(
  id integer NOT NULL,
  "timeOrdered" timestamp NOT NULL,
  "totalCost" float,
  payment text,
  PRIMARY KEY("ticketID")
);

ALTER TABLE "foodIngredient"
  ADD CONSTRAINT "foodIngredient_foodID_fkey"
    FOREIGN KEY ("foodID") REFERENCES food ("foodID");

ALTER TABLE ingredient
  ADD CONSTRAINT "ingredient_ingredientID_fkey"
    FOREIGN KEY ("ingredientID") REFERENCES "foodIngredient" ("ingredientID");

ALTER TABLE ticket
  ADD CONSTRAINT ticket_id_fkey
    FOREIGN KEY (id) REFERENCES "foodTicket" ("ticketID");

ALTER TABLE food
  ADD CONSTRAINT "food_foodID_fkey"
    FOREIGN KEY ("foodID") REFERENCES "foodTicket" ("foodID");
