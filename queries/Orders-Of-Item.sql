SELECT t."ticketID", t."timeOrdered", t."totalCost", t.payment
FROM ticket t
JOIN foodTicket ft ON t."ticketID" = ft."ticketID"
JOIN food f ON ft."foodID" = f."foodID"
WHERE f."name" = 'Hamburger';

-- lists all the orders that have a certain item