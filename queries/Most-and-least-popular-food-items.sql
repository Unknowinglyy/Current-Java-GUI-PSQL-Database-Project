SELECT food_item, order_count
FROM (
    SELECT f.name AS food_item, COUNT(ft."ticketID") AS order_count
    FROM food f
    JOIN "foodticket" ft ON f."foodID" = ft."foodID"
    GROUP BY f.name
    ORDER BY order_count DESC
    LIMIT 5
) AS top_orders

UNION ALL

SELECT food_item, order_count
FROM (
    SELECT f.name AS food_item, COUNT(ft."ticketID") AS order_count
    FROM food f
    JOIN "foodticket" ft ON f."foodID" = ft."foodID"
    GROUP BY f.name
    ORDER BY order_count
    LIMIT 5
) AS bottom_orders;
--shows the top5 and bottom 5 items
