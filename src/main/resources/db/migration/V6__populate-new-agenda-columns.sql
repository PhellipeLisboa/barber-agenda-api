UPDATE agenda
SET workday_start = '08:00:00', workday_end = '17:00:00'
WHERE workday_start IS NULL OR workday_end IS NULL;