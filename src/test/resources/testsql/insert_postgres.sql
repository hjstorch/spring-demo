INSERT INTO PRODUCT (ID, NAME, DESCRIPTION) VALUES
                           (GEN_RANDOM_UUID(),'P1','Product one'),
                           (GEN_RANDOM_UUID(),'P2','Product two');
INSERT INTO COUNTRY (ID, NAME) VALUES
                           ('49', 'Deutschland'),
                           ('44', 'united kingdom');
INSERT INTO TAX (LOCALE, RATE_REDUCED, RATE_NORMAL, COUNTRY_ID) VALUES
                           ('DE_DE', 7, 19, '49'),
                           ('EN_UK', 5, 20, '44');

commit;