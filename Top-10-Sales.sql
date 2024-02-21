SELECT DATE_TRUNC('day', t."timeOrdered") AS order_date, SUM(t."totalCost") AS total_amount
FROM ticket t
GROUP BY order_date
ORDER BY total_amount DESC
LIMIT 10;
--shows the days with the top 10 most  expensive orders in terms of cost per day.