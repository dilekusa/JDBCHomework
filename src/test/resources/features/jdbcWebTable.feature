@jdbcWeb
Feature: Adding new people from database

Scenario: Test the functionality of database and webtable

When I connect to MySql Database
And I execute following SQL query "SELECT first_name, last_name, job_id, city, phone_number, hire_date,salary FROM employees e JOIN departments d ON e.department_id=d.department_id JOIN locations l ON d.location_id=l.location_id "
And I navigate to Editors Employees table
Then Employees table should be displayed
When I create a new employee
Then Webtable data and Database data should match