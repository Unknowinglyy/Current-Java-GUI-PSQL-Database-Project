SELECT DATE_TRUNC('hour', "timeOrdered") AS hour_timestamp, 
       COUNT(*) AS order_count,
       SUM("totalCost") AS total_sales
FROM ticket
GROUP BY hour_timestamp
ORDER BY hour_timestamp;
--lists sales per hour
