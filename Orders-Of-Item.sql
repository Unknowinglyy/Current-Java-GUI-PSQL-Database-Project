SELECT f.name AS food_item, COUNT(t.id) AS order_count, SUM(t."totalCost") AS total_amount
FROM food f
JOIN "foodTicket" ft ON f."foodID" = ft."foodID"
JOIN ticket t ON ft."ticketID" = t.id
WHERE f.name = 'specific_food_item'
GROUP BY f.name;