SELECT f."foodType", SUM(t."totalCost") AS total_revenue
FROM food f
JOIN "foodticket" ft ON f."foodID" = ft."foodID"
JOIN ticket t ON ft."ticketID" = t."ticketID"
GROUP BY f."foodType";
