SELECT i."name" AS ingredient_name
FROM foodingredient fi
JOIN ingredient i ON fi."ingredientID" = i."ingredientID"
JOIN food f ON fi."foodID" = f."foodID"
WHERE f."name" = 'specific_food_item';
