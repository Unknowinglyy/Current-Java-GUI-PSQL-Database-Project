SELECT f."foodType", SUM(t."totalCost") AS total_revenue
FROM food f
JOIN "foodTicket" ft ON f."foodID" = ft."foodID"
JOIN ticket t ON ft."ticketID" = t.id
GROUP BY f."foodType";