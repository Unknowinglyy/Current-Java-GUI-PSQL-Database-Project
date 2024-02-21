SELECT f."name" AS food_item, AVG(t."totalCost") AS avg_order_cost
FROM food f
JOIN "foodticket" ft ON f."foodID" = ft."foodID"
JOIN ticket t ON ft."ticketID" = t."ticketID"
GROUP BY f."name";

--shows the average cost of orders containing each item