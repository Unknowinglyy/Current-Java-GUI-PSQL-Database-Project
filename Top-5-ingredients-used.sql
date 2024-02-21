SELECT i.name AS ingredient, COUNT(fi."foodID") AS usage_frequency
FROM ingredient i
JOIN "foodIngredient" fi ON i."ingredientID" = fi."ingredientID"
GROUP BY i.name
ORDER BY usage_frequency DESC
LIMIT 5;