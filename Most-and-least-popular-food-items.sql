SELECT f.name AS food_item, COUNT(ft."ticketID") AS order_count
FROM food f
JOIN "foodTicket" ft ON f."foodID" = ft."foodID"
GROUP BY f.name
ORDER BY order_count DESC
LIMIT 5;

UNION ALL

SELECT f.name AS food_item, COUNT(ft."ticketID") AS order_count
FROM food f
JOIN "foodTicket" ft ON f."foodID" = ft."foodID"
GROUP BY f.name
ORDER BY order_count
LIMIT 5;