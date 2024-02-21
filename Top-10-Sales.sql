SELECT DATE_TRUNC('day', t."timeOrdered") AS order_date, SUM(t."totalCost") AS total_amount
FROM ticket t
GROUP BY order_date
ORDER BY total_amount DESC
LIMIT 10;
