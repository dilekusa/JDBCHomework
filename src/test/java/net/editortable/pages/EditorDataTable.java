package net.editortable.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class EditorDataTable {

	public EditorDataTable() {
		PageFactory.initElements(Driver.getInstance(), this);
	}

	@FindBy(css = "table#example.display.dataTable")
	public WebElement table;

	@FindBy(css = "a.dt-button.buttons-create")
	public WebElement newButton;

	@FindBy(css = "#DTE_Field_first_name")
	public WebElement firstName;

	@FindBy(css = "#DTE_Field_last_name")
	public WebElement lastName;

	@FindBy(css = "#DTE_Field_position")
	public WebElement position;

	@FindBy(css = "#DTE_Field_office")
	public WebElement office;

	@FindBy(css = "#DTE_Field_extn")
	public WebElement extension;

	@FindBy(css = "#DTE_Field_start_date")
	public WebElement startDate;

	@FindBy(css = "#DTE_Field_salary")
	public WebElement salary;

	@FindBy(css = "button.btn")
	public WebElement createBut;

	@FindBy(css = "input[type='search']")
	public WebElement search;
	

}
