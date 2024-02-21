SELECT 
  DATE_PART('year', "timeOrdered") AS year, 
  DATE_PART('week', "timeOrdered") AS week_number, 
  COUNT(*) AS order_count
FROM ticket
GROUP BY year, week_number
ORDER BY year, week_number;
--lists orders per week
