SELECT DATE_PART('week', t."timeOrdered") AS week_number, SUM(t."totalCost") AS weekly_revenue
FROM ticket t
GROUP BY week_number
ORDER BY week_number;
--shows revenue on a weekly basis