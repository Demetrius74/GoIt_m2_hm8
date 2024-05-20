SELECT 'Eldest' AS type, name, birthday
FROM worker
WHERE birthday IN (
    SELECT min(birthday)
    FROM worker
)
UNION
SELECT 'Youngest' AS type, name, birthday
FROM worker
WHERE birthday IN (
    SELECT max(birthday)
    FROM worker
);