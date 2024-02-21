SELECT DATE_TRUNC('day', "timeOrdered") AS day, SUM("totalCost") AS total_sales
FROM ticket
GROUP BY day
ORDER BY total_sales DESC
LIMIT 2;