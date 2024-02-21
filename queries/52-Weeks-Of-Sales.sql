SELECT 
  (DATE_PART('year', "timeOrdered") - 2022) * 52 + DATE_PART('week', "timeOrdered") AS week_number, 
  COUNT(*) - 7 AS order_count
FROM ticket
GROUP BY week_number
ORDER BY week_number;
--lists orders per week
