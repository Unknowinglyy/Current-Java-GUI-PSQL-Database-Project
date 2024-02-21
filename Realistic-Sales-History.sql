SELECT DATE_PART('hour', "timeOrdered") AS hour_of_day, COUNT(*) AS order_count, SUM("totalCost") AS total_sales
FROM ticket
GROUP BY hour_of_day
ORDER BY hour_of_day;