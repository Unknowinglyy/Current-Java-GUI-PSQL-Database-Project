<<<<<<< HEAD
SELECT DATE_PART('week', "timeOrdered") AS week_number, COUNT(*) AS order_count
FROM ticket
GROUP BY week_number
ORDER BY week_number;
--lists orders per week
=======
SELECT 
  (DATE_PART('year', "timeOrdered") - 2022) * 52 + DATE_PART('week', "timeOrdered") AS week_number, 
  COUNT(*) - 7 AS order_count
FROM ticket
GROUP BY week_number
ORDER BY week_number;
--lists orders per week
>>>>>>> 274593bbb79d5cd125a9ff074415e1a5deea22cb
