SELECT EXTRACT(HOUR FROM t."timeOrdered") AS hour_of_day, COUNT(t.id) AS order_count, SUM(t."totalCost") AS total_amount
FROM ticket t
GROUP BY hour_of_day
ORDER BY hour_of_day;