SELECT DATE_PART('week', "timeOrdered") AS week_number, COUNT(*) AS order_count
FROM ticket
GROUP BY week_number
ORDER BY week_number;
--lists orders per week
