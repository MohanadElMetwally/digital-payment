-- Seeder: Billers

INSERT INTO billers (id, name, category, service_provider, created_at) VALUES

-- WATER_PROVIDER
(gen_random_uuid(), 'Water Provider North', 'WATER', 'WATER_PROVIDER', NOW()),
(gen_random_uuid(), 'Water Provider South', 'WATER', 'WATER_PROVIDER', NOW()),

-- ELECTRICITY_PROVIDER
(gen_random_uuid(), 'Electricity Provider East',  'ELECTRICITY', 'ELECTRICITY_PROVIDER', NOW()),
(gen_random_uuid(), 'Electricity Provider West',  'ELECTRICITY', 'ELECTRICITY_PROVIDER', NOW()),

-- GAS_PROVIDER
(gen_random_uuid(), 'Gas Provider North', 'GAS', 'GAS_PROVIDER', NOW()),
(gen_random_uuid(), 'Gas Provider East',  'GAS', 'GAS_PROVIDER', NOW()),

-- INTERNET_PROVIDER
(gen_random_uuid(), 'Internet Provider South', 'INTERNET', 'INTERNET_PROVIDER', NOW()),
(gen_random_uuid(), 'Internet Provider West',  'INTERNET', 'INTERNET_PROVIDER', NOW()),

-- TELEPHONE_PROVIDER
(gen_random_uuid(), 'Telephone Provider North', 'TELEPHONE', 'TELEPHONE_PROVIDER', NOW()),
(gen_random_uuid(), 'Telephone Provider East',  'TELEPHONE', 'TELEPHONE_PROVIDER', NOW());
