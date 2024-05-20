SELECT name, subquery.count_project
FROM client
LEFT OUTER JOIN (
    SELECT client_id, count(id) AS count_project
    FROM project
    GROUP BY client_id
) AS subquery ON subquery.client_id = client.id
WHERE count_project IN (
    SELECT count(id)
    FROM project
    GROUP BY client_id
    ORDER BY count(id) DESC
    LIMIT 1
);