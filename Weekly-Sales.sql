SELECT DATE_PART('week', t."timeOrdered") AS week_number, COUNT(t.id) AS order_count
FROM ticket t
GROUP BY week_number
ORDER BY week_number;