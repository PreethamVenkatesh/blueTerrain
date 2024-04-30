-- Run the whole queries at once.
use BlueTerrain_Restaurant;

ALTER TABLE customers ADD COLUMN number_of_visits INT DEFAULT 0;

DROP TABLE staffs;

CREATE TABLE staffs (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR (50),
    last_name VARCHAR (50),
    email VARCHAR (80),
    profile_type VARCHAR (20),
    isApproved BOOLEAN,
    hours_worked INT
);

INSERT INTO staffs (first_name, last_name, email, profile_type, isApproved, hours_worked)
VALUES ('Virat', 'Kohli', 'virat.kohli@swansea.ac.uk', 'Manager', FALSE, 12);

INSERT INTO staffs (first_name, last_name, email, profile_type, isApproved, hours_worked)
VALUES ('Ola', 'Toye', 'olaayantoye@swansea.ac.uk', 'Manager', TRUE, 350);
-- Add your own name.

DROP TABLE menu;

CREATE TABLE Menu (
    MenuId INT AUTO_INCREMENT PRIMARY KEY,
    itemValue DECIMAL,
    ItemName VARCHAR (100),
    ItemType VARCHAR (20),
    purchaseCount INT
);


INSERT INTO Menu (ItemName, ItemValue, ItemType, purchaseCount)
VALUES
('Crispy and Salted Belly Pork Squares with Bourbon Glaze and Dates Puree', 8.95, 'Starter', FLOOR(1 + RAND() * 100)),
('Soup of the Day , Served with Truffle Oil', 7.25, 'Starter', FLOOR(1 + RAND() * 100)),
('Patatas Bravas with Homemade Spicy Brava Sauce & Aioli', 7.50, 'Starter', FLOOR(1 + RAND() * 100)),
('Two Grilled Fresh Spanish Chorizo, on a Bed of Caramelised Onions', 9.25, 'Starter', FLOOR(1 + RAND() * 100)),
('Canarian Salty Glazed Potatoes with a Duo of Canarian Mojo Sauces', 6.95, 'Starter', FLOOR(1 + RAND() * 100)),
('Meatballs in a Homemade Tomato Sauce', 8.50, 'Starter', FLOOR(1 + RAND() * 100)),
('Trio of dips , Aubergine Pate, Piquillo Peppers Dip & Olive Pate', 7.50, 'Starter', FLOOR(1 + RAND() * 100)),
('Traditional Croquettes Served with Homemade Alioli Sauce', 8.75, 'Starter', FLOOR(1 + RAND() * 100)),
('Duck & Pork Liver Pate with Orange & Cognac', 8.25, 'Starter', FLOOR(1 + RAND() * 100)),
('Fried Spanish Padron Peppers, Alioli, Maldon Salt & Extra Virgin Olive Oil', 7.95, 'Starter', FLOOR(1 + RAND() * 100)),
('Fresh Whitstable Oysters', 2.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Mussels Al Marinara', 8.50, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Rock Lobster Tail', 21.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Deep Fried Calamares', 7.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Deep Fried Cod Goujons', 8.25, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Tripled Cooked Octopus', 13.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Gambas Al Ajillo', 11.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Grilled Sardines', 7.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('Pan Fried Scallops', 13.95, 'Fish_Menu', FLOOR(1 + RAND() * 100)),
('8oz Grilled Fillet Steak Medallions (Peppercorn Sauce)', 29.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('7 oz Fillet Steak Served with a Spanish Pisto & Roasted Tomato', 29.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('10 oz Ribeye Steak Served with a Spanish Pisto & Roasted Tomato', 27.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('16 oz T-Bone Steak Served with a Spanish Pisto & Roasted Tomato', 33.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('Slow Roasted Lamb Shank Served with Red Rioja Wine & Rosemary Sauce', 19.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('Chicken Breast Stuffed with Spinach, Spanish Tetilla Cheese & Sweet Date Paste', 17.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('Slow Cooked Pork Belly, Served with Spanish Cider, Apples & Pear Sauce', 18.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('Tagliatelle A La Marinera, Seafood and Served in a Seafood Tomato Sauce', 18.95, 'Grill_Meat', FLOOR(1 + RAND() * 100)),
('Vegan Ravioli stuffed with peas and mint on a Bed of piquillo pepper sauce', 17.95, 'Vegan', FLOOR(1 + RAND() * 100)),
('Beetroot, roasted red Onion on a bed of Rich Tomato and mushrooms Sauce', 17.95, 'Vegan', FLOOR(1 + RAND() * 100)),
('Jerk Chicken Half', 15.20, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Jerk Chicken Breast', 14.00, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Jerk Lamb', 19.00, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Jerk Pork Belly', 16.50, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Rum BBQ Ribs', 15.50, 'Meat Main', FLOOR(1 + RAND() * 100)),
('MoBay Chicken', 14.40, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Jerk Salmon', 14.80, 'Meat Main', FLOOR(1 + RAND() * 100)),
('Vegan Jerk Chicken', 14.00, 'Meat Main', FLOOR(1 + RAND() * 100));


-- Insert sample customers
INSERT INTO customers (first_name, last_name, email, address, number_of_visits) VALUES
('Chinedu', 'Okoro', 'chinedu.okoro@example.com', '24 Opebi Road, Ikeja, Lagos', FLOOR(1 + RAND() * 10)),
('Amaka', 'Njoku', 'amaka.njoku@example.com', '55 Gana Street, Maitama, Abuja', FLOOR(1 + RAND() * 10)),
('Tolu', 'Badejo', 'tolu.badejo@example.com', '120 Awolowo Road, Ikoyi, Lagos', FLOOR(1 + RAND() * 10)),
('Ifeanyi', 'Chukwu', 'ifeanyi.chukwu@example.com', '15 Wetheral Road, Owerri, Imo State', FLOOR(1 + RAND() * 10)),
('Yemi', 'Adeola', 'yemi.adeola@example.com', '10 VGC, Lekki, Lagos', FLOOR(1 + RAND() * 10)),
('Uche', 'Mbakwe', 'uche.mbakwe@example.com', '100 Chime Avenue, New Haven, Enugu', FLOOR(1 + RAND() * 10)),
('Ngozi', 'Eze', 'ngozi.eze@example.com', '23 Sultan Road, Kaduna, Kaduna State', FLOOR(1 + RAND() * 10)),
('Emeka', 'Okafor', 'emeka.okafor@example.com', '2 Plateau Street, Jos, Plateau State', FLOOR(1 + RAND() * 10));
