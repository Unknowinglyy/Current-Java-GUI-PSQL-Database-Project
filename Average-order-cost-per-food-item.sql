SELECT f.name AS food_item, AVG(t."totalCost") AS avg_order_cost
FROM food f
JOIN "foodTicket" ft ON f."foodID" = ft."foodID"
JOIN ticket t ON ft."ticketID" = t.id
GROUP BY f.name;