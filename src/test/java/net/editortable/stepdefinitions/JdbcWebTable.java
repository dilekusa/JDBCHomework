package net.editortable.stepdefinitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.editortable.pages.EditorDataTable;
import utilities.ConfigurationReader;
import utilities.DBUtility;
import utilities.DBUtility.DBType;
import utilities.Driver;


public class JdbcWebTable {
	WebDriver driver=Driver.getInstance();
	List<String[]> queryResultList;

	EditorDataTable homePage=new EditorDataTable();

	@When("^I connect to MySql Database$")
	public void i_connect_to_MySql_Database() throws Throwable {
		DBUtility.establishConnection(DBType.MYSQL);
	}

	@When("^I execute following SQL query \"([^\"]*)\"$")
	public void i_execute_following_SQL_query(String query) throws Throwable {
		queryResultList = DBUtility
				.runSQLQuery("SELECT first_name, last_name, job_id, city, phone_number, hire_date, salary "
						+ "FROM employees e join departments d " + "ON e.department_id=d.department_id "
						+ "JOIN locations l " + "ON d.location_id=l.location_id ");
		DBUtility.closeConnections();

	}

	@When("^I navigate to Editors Employees table$")
	public void i_navigate_to_Editors_Employees_table() throws Throwable {

		driver.get(ConfigurationReader.getProperty("url"));
		
	}

	@Then("^Employees table should be displayed$")
	public void employees_table_should_be_displayed() throws Throwable {
		
		Assert.assertTrue(homePage.table.isDisplayed());
		

	}

	@When("^I create a new employee$")
	public void i_create_a_new_employee() throws Throwable {
		DBUtility.establishConnection(DBType.MYSQL);

		queryResultList = DBUtility
				.runSQLQuery("SELECT first_name, last_name, job_id, city, phone_number, hire_date, salary "
						+ "FROM employees e join departments d " + "ON e.department_id=d.department_id "
						+ "JOIN locations l " + "ON d.location_id=l.location_id ");

		DBUtility.closeConnections();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		for (int rowNum = 0; rowNum <queryResultList.size() ; rowNum++) {
			
			
			homePage.newButton.click();
			

			String[] rowData = queryResultList.get(rowNum);
			String firstName = rowData[0];
			String lastName = rowData[1];
			String position = rowData[2];
			String office = rowData[3];
			String extension = rowData[4].substring(rowData[4].length()-4);
			String startDate = rowData[5];
			String salary = rowData[6];

			homePage.firstName.sendKeys(firstName);
			homePage.lastName.sendKeys(lastName);
			homePage.position.sendKeys(position);
			homePage.office.sendKeys(office);
			homePage.extension.sendKeys(extension);
			homePage.startDate.sendKeys(startDate);
			homePage.salary.sendKeys(salary);
			
			homePage.createBut.click();
			
			
			WebElement create = driver.findElement(By.cssSelector("button.btn"));
			create.click();

			wait.until(ExpectedConditions.invisibilityOf(create));
			
		}
	}

	@Then("^Webtable data and Database data should match$")
	public void webtable_data_and_Database_data_should_match() throws Throwable {

		for (int i = 0; i <queryResultList.size(); i++) {
			
			String[] rowData = queryResultList.get(i);
			String name = rowData[0] + " " + rowData[1];
			String position = rowData[2];
			String office = rowData[3];
			String extension = rowData[4].substring(rowData[4].length()-4);
			System.out.println("database extension is"+extension);
			String startDate = rowData[5];
			String salary = rowData[6].replace(".00", "");
			
			homePage.search.sendKeys(name);

			WebElement table = driver.findElement(By.id("example"));
			/*List<WebElement> row = table.findElements(By.xpath("tbody/tr/td"));
			System.out.println("row size"+ row.size());*/
			List<WebElement> cells = table.findElements(By.xpath("tbody/tr/td"));
			
			System.out.println("cell size is:"+ cells.size());

			String nameTable = cells.get(0).getText();
			String posTable = cells.get(1).getText();
			String offTable = cells.get(2).getText();
			String ExtTable = cells.get(3).getText();
			System.out.println("extension from table is "+ExtTable);
			String startTable = cells.get(4).getText();
			String salaryTable = cells.get(5).getText().replace("$", "").replace(",", "");
			
			System.out.println("the salary of"+nameTable+" is "+ salaryTable+"database sal: "+salary);
		
			
			Assert.assertTrue(name.equalsIgnoreCase(nameTable));
			Assert.assertTrue(position.equalsIgnoreCase(posTable));
			Assert.assertTrue(office.equalsIgnoreCase(offTable));
			Assert.assertTrue(extension.equals(ExtTable));
			Assert.assertTrue(startDate.equals(startTable));
			Assert.assertTrue(salary.equals(salaryTable));
			
			
			homePage.search.clear();

		}
	}

}
