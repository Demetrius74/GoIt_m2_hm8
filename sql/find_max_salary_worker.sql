SELECT name, salary FROM worker
WHERE salary IN (
    SELECT max(salary) FROM worker
);