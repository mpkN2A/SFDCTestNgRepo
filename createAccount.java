package sdfcTestNGPackage;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class createAccount {
	public WebDriver objdriver;

	String strLoginURL = "https://login.salesforce.com/";
	String strValidUsername = "harrypotter@abc.com";
	String strValidPw = "Open123$#SalesF";

	@BeforeClass
	public void CreateDriverInstance() {

		WebDriverManager.chromedriver().setup();
		objdriver = new ChromeDriver();
		objdriver.manage().window().maximize();

	}

	@BeforeMethod
	public void loadLoginPage() {
		objdriver.get(strLoginURL);
		String strExpectedTitle = "Login | Salesforce";
		String strResultTitle = objdriver.getTitle();
		org.testng.Assert.assertEquals(strResultTitle, strExpectedTitle, "Error in loading SDFC Login Page.");
		System.out.println("SFDC Home page displayed Successfully.");
		// Login
		objdriver.findElement(By.id("username")).sendKeys(strValidUsername);
		objdriver.findElement(By.id("password")).sendKeys(strValidPw);
		objdriver.findElement(By.id("Login")).click();

	}
	@Test
	public void createAnAccount()
	{
		// ClickAccount link on Home Page
		objdriver.findElement(By.xpath("//li[@id='Account_Tab']//a[1]"));
		String strExpeTitle ="Home Page ~ Salesforce - Developer Edition";
		String strActualTitle=objdriver.getTitle();
		Assert.assertEquals(strActualTitle, strExpeTitle,"Failed to display Account Page.");
		//click Account Tab
		objdriver.findElement(By.xpath("//li[@id='Account_Tab']/a[1]")).click();
		
		// Click New button
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='bodyCell']//descendant::input[@name='new']")));
		objdriver.findElement(By.xpath("//td[@id='bodyCell']//descendant::input[@name='new']")).click();
		String strExpAccPage ="Account Edit: New Account ~ Salesforce - Developer Edition";
		String strActAccPage = objdriver.getTitle();
		Assert.assertEquals(strActAccPage, strExpAccPage,"Failed to display NEW Account Page.");
	    // Add New Account Name
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='acc2']")));
		
		
		
		//Type new Account
		objdriver.findElement(By.xpath("//input[@id='acc2']")).sendKeys("Aug8account");
		// CLick Save button
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(("//td[@id='topButtonRow']/input[@name='save']"))));
		objdriver.findElement(By.xpath("//td[@id='topButtonRow']/input[@name='save']"));
		// Check if it  is redirected to new acoount page 
		
		//div[@id='contactHeaderRow']//h2[1]
	}

}
