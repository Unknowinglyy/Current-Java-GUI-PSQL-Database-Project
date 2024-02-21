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

