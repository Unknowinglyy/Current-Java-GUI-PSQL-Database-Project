SELECT f.name AS menu_item, COUNT(fi."ingredientID") AS inventory_items_count
FROM food f
JOIN foodIngredient fi ON f."foodID" = fi."foodID"
GROUP BY f.name
LIMIT 20;
--retrieves the names of menu items and their corresponding count of
--inventory items required, grouped by menu item, limited to the first 
-- 20 items.