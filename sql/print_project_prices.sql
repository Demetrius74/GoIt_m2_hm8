SELECT project.name, sum(salary) * datediff(month, start_date, finish_date) AS price
FROM project_worker
JOIN project ON project.id = project_worker.project_id
JOIN worker ON worker.id = project_worker.worker_id
GROUP BY project.name
ORDER BY price DESC;