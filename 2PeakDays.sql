SELECT DATE_TRUNC('day', "timeOrdered") AS day_timestamp, 
       SUM("totalCost") AS total_sales
FROM ticket
GROUP BY day_timestamp
ORDER BY total_sales DESC
LIMIT 2;
