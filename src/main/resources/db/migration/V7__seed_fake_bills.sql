-- V3__seed_fake_bills.sql

INSERT INTO fake_bills (id, customer_number, customer_name, provider, amount, currency, status, billing_period_start, billing_period_end, due_date, created_at)
VALUES
    -- CUST-001: Mohanad ElMetwally ElMarakby (Water)
    (gen_random_uuid(), 'CUST-001', 'Mohanad ElMetwally ElMarakby', 'WATER_PROVIDER', 185.50,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 09:00:00'),

    -- CUST-002: Mohanad ElMetwally ElMarakby (Electricity)
    (gen_random_uuid(), 'CUST-002', 'Mohanad ElMetwally ElMarakby', 'ELECTRICITY_PROVIDER', 542.75,  'EGP', 'UNPAID',     '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 09:05:00'),

    -- CUST-003: Mohanad ElMetwally ElMarakby (Gas)
    (gen_random_uuid(), 'CUST-003', 'Mohanad ElMetwally ElMarakby', 'GAS_PROVIDER', 210.00,  'EGP', 'UNPAID',  '2026-03-01', '2026-03-31', '2026-04-15', '2026-03-31 09:10:00'),

    -- CUST-004: Ahmed Mostafa Khalil (Internet)
    (gen_random_uuid(), 'CUST-004', 'Ahmed Mostafa Khalil', 'INTERNET_PROVIDER', 399.00,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 10:00:00'),

    -- CUST-005: Fatma Ibrahim ElSayed (Telephone)
    (gen_random_uuid(), 'CUST-005', 'Fatma Ibrahim ElSayed', 'TELEPHONE_PROVIDER', 155.25,  'EGP', 'UNPAID',     '2026-04-01', '2026-04-30', '2026-05-10', '2026-04-11 10:15:00'),

    -- CUST-006: Omar Youssef Naguib (Water)
    (gen_random_uuid(), 'CUST-006', 'Omar Youssef Naguib', 'WATER_PROVIDER', 220.00,  'EGP', 'UNPAID',  '2026-03-01', '2026-03-31', '2026-04-15', '2026-03-31 11:00:00'),

    -- CUST-007: Nour Tarek Mansour (Electricity)
    (gen_random_uuid(), 'CUST-007', 'Nour Tarek Mansour', 'ELECTRICITY_PROVIDER', 618.50,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 11:30:00'),

    -- CUST-008: Mona Hossam ElDin Farouk (Gas)
    (gen_random_uuid(), 'CUST-008', 'Mona Hossam ElDin Farouk', 'GAS_PROVIDER', 195.00,  'EGP', 'UNPAID',     '2026-04-01', '2026-04-30', '2026-05-10', '2026-04-11 12:00:00'),

    -- CUST-009: Khaled Samir Abdelnasser (Internet)
    (gen_random_uuid(), 'CUST-009', 'Khaled Samir Abdelnasser', 'INTERNET_PROVIDER', 450.00,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 12:30:00'),

    -- CUST-010: Dina Walid ElGhazali (Telephone)
    (gen_random_uuid(), 'CUST-010', 'Dina Walid ElGhazali', 'TELEPHONE_PROVIDER', 178.75,  'EGP', 'UNPAID',  '2026-03-01', '2026-03-31', '2026-04-15', '2026-03-31 13:00:00'),

    -- CUST-011: Youssef Adel Barakat (Water)
    (gen_random_uuid(), 'CUST-011', 'Youssef Adel Barakat', 'WATER_PROVIDER', 165.00,  'EGP', 'UNPAID',     '2026-04-01', '2026-04-30', '2026-05-10', '2026-04-11 13:30:00'),

    -- CUST-012: Rania Mahmoud Shawki (Electricity)
    (gen_random_uuid(), 'CUST-012', 'Rania Mahmoud Shawki', 'ELECTRICITY_PROVIDER', 730.25,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 14:00:00'),

    -- CUST-013: Hassan Amr ElKady (Gas)
    (gen_random_uuid(), 'CUST-013', 'Hassan Amr ElKady', 'GAS_PROVIDER', 240.50,  'EGP', 'UNPAID',  '2026-03-01', '2026-03-31', '2026-04-15', '2026-03-31 14:30:00'),

    -- CUST-014: Sara Ehab Zaki (Internet)
    (gen_random_uuid(), 'CUST-014', 'Sara Ehab Zaki', 'INTERNET_PROVIDER', 399.00,  'EGP', 'UNPAID',     '2026-04-01', '2026-04-30', '2026-05-10', '2026-04-11 15:00:00'),

    -- CUST-015: Karim Fathy ElBanna (Telephone)
    (gen_random_uuid(), 'CUST-015', 'Karim Fathy ElBanna', 'TELEPHONE_PROVIDER', 205.00,  'EGP', 'UNPAID',   '2026-04-01', '2026-04-30', '2026-05-15', '2026-04-11 15:30:00');