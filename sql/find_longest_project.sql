SELECT name, datediff(month, start_date, finish_date) as month_count
FROM project
GROUP BY name
HAVING month_count IN (
    SELECT datediff(month, start_date, finish_date) as month_count
    FROM project
    GROUP BY id
    ORDER BY month_count DESC
    LIMIT 1
);