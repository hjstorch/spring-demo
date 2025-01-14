INSERT INTO COUNTRY (id, name)
VALUES
    ('1', 'United States'),
    ('2', 'Canada'),
    ('44', 'United Kingdom'),
    ('49', 'Germany'),
    ('39', 'Italy'),
    ('34', 'Spain'),
    ('61', 'Australia'),
    ('91', 'India'),
    ('81', 'Japan');

INSERT INTO TAX (locale, country_id, rate_reduced, rate_normal)
VALUES
    ('en_US', '1', 5, 8), -- Sales tax rates for the US
    ('fr_CA', '2', 12, 15), -- Provincial sales tax rates for Canada
    ('en_GB', '44', 20, 25), -- VAT rates for the UK
    ('de_DE', '49', 7, 19), -- USt rates for Germany
    ('it_IT', '39', 10, 22), -- IVA rates for Italy
    ('es_ES', '34', 4, 21), -- IVA rates for Spain
    ('en_AU', '61', 10, 12.5), -- GST rates for Australia
    ('en_IN', '91', 5, 8), -- CGST and SGST rates for India
    ('ja_JP', '81', 5, 10); -- Consumption tax rates for Japan

INSERT INTO PRODUCT (id, name, description, price)
VALUES
    (GEN_RANDOM_UUID(), 'Product 1', 'Description 1', 9.99),
    (GEN_RANDOM_UUID(), 'Product 2', 'Description 2', 19.99),
    (GEN_RANDOM_UUID(), 'Product 3', 'Description 3', 29.99),
    (GEN_RANDOM_UUID(), 'Product 4', 'Description 4', 39.99),
    (GEN_RANDOM_UUID(), 'Product 5', 'Description 5', 49.99),
    (GEN_RANDOM_UUID(), 'Product 6', 'Description 6', 59.99),
    (GEN_RANDOM_UUID(), 'Product 7', 'Description 7', 69.99),
    (GEN_RANDOM_UUID(), 'Product 8', 'Description 8', 79.99),
    (GEN_RANDOM_UUID(), 'Product 9', 'Description 9', 89.99),
    (GEN_RANDOM_UUID(), 'Product 11', 'Description 11', 9.99),
    (GEN_RANDOM_UUID(), 'Product 12', 'Description 12', 19.99),
    (GEN_RANDOM_UUID(), 'Product 13', 'Description 13', 29.99),
    (GEN_RANDOM_UUID(), 'Product 14', 'Description 14', 39.99),
    (GEN_RANDOM_UUID(), 'Product 15', 'Description 15', 49.99),
    (GEN_RANDOM_UUID(), 'Product 16', 'Description 16', 59.99),
    (GEN_RANDOM_UUID(), 'Product 17', 'Description 17', 69.99),
    (GEN_RANDOM_UUID(), 'Product 18', 'Description 18', 79.99),
    (GEN_RANDOM_UUID(), 'Product 19', 'Description 19', 89.99),
    (GEN_RANDOM_UUID(), 'Product 21', 'Description 21', 9.99),
    (GEN_RANDOM_UUID(), 'Product 22', 'Description 22', 19.99),
    (GEN_RANDOM_UUID(), 'Product 23', 'Description 23', 29.99),
    (GEN_RANDOM_UUID(), 'Product 24', 'Description 24', 39.99),
    (GEN_RANDOM_UUID(), 'Product 25', 'Description 25', 49.99),
    (GEN_RANDOM_UUID(), 'Product 26', 'Description 26', 59.99),
    (GEN_RANDOM_UUID(), 'Product 27', 'Description 27', 69.99),
    (GEN_RANDOM_UUID(), 'Product 28', 'Description 28', 79.99),
    (GEN_RANDOM_UUID(), 'Product 29', 'Description 29', 89.99),
    (GEN_RANDOM_UUID(), 'Product 31', 'Description 31', 9.99),
    (GEN_RANDOM_UUID(), 'Product 32', 'Description 32', 19.99),
    (GEN_RANDOM_UUID(), 'Product 33', 'Description 33', 29.99),
    (GEN_RANDOM_UUID(), 'Product 34', 'Description 34', 39.99),
    (GEN_RANDOM_UUID(), 'Product 35', 'Description 35', 49.99),
    (GEN_RANDOM_UUID(), 'Product 36', 'Description 36', 59.99),
    (GEN_RANDOM_UUID(), 'Product 37', 'Description 37', 69.99),
    (GEN_RANDOM_UUID(), 'Product 38', 'Description 38', 79.99),
    (GEN_RANDOM_UUID(), 'Product 39', 'Description 39', 89.99),
    (GEN_RANDOM_UUID(), 'Product 41', 'Description 41', 9.99),
    (GEN_RANDOM_UUID(), 'Product 42', 'Description 42', 19.99),
    (GEN_RANDOM_UUID(), 'Product 43', 'Description 43', 29.99),
    (GEN_RANDOM_UUID(), 'Product 44', 'Description 44', 39.99),
    (GEN_RANDOM_UUID(), 'Product 45', 'Description 45', 49.99),
    (GEN_RANDOM_UUID(), 'Product 46', 'Description 46', 59.99),
    (GEN_RANDOM_UUID(), 'Product 47', 'Description 47', 69.99),
    (GEN_RANDOM_UUID(), 'Product 48', 'Description 48', 79.99),
    (GEN_RANDOM_UUID(), 'Product 49', 'Description 49', 89.99),
    (GEN_RANDOM_UUID(), 'Product 51', 'Description 51', 9.99),
    (GEN_RANDOM_UUID(), 'Product 52', 'Description 52', 19.99),
    (GEN_RANDOM_UUID(), 'Product 53', 'Description 53', 29.99),
    (GEN_RANDOM_UUID(), 'Product 54', 'Description 54', 39.99),
    (GEN_RANDOM_UUID(), 'Product 55', 'Description 55', 49.99),
    (GEN_RANDOM_UUID(), 'Product 56', 'Description 56', 59.99),
    (GEN_RANDOM_UUID(), 'Product 57', 'Description 57', 69.99),
    (GEN_RANDOM_UUID(), 'Product 58', 'Description 58', 79.99),
    (GEN_RANDOM_UUID(), 'Product 59', 'Description 59', 89.99),
    (GEN_RANDOM_UUID(), 'Product 61', 'Description 61', 9.99),
    (GEN_RANDOM_UUID(), 'Product 62', 'Description 62', 19.99),
    (GEN_RANDOM_UUID(), 'Product 63', 'Description 63', 29.99),
    (GEN_RANDOM_UUID(), 'Product 64', 'Description 64', 39.99),
    (GEN_RANDOM_UUID(), 'Product 65', 'Description 65', 49.99),
    (GEN_RANDOM_UUID(), 'Product 66', 'Description 66', 59.99),
    (GEN_RANDOM_UUID(), 'Product 67', 'Description 67', 69.99),
    (GEN_RANDOM_UUID(), 'Product 68', 'Description 68', 79.99),
    (GEN_RANDOM_UUID(), 'Product 69', 'Description 69', 89.99);