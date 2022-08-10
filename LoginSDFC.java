package sdfcTestNGPackage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSDFC {
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

	}

	@Test(enabled = false)
	public void loginErrorMessage() {
		String strExpectedErrormsg = "Please enter your password.";
		objdriver.findElement(By.id("username")).sendKeys(strValidUsername);
		objdriver.findElement(By.id("password")).clear();
		objdriver.findElement(By.id("Login")).click();

		WebDriverWait objWait = new WebDriverWait(objdriver, Duration.ofSeconds(40));
		objWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("error")));

		String strActualErrormsg = objdriver.findElement(By.id("error")).getText();
		Assert.assertEquals(strActualErrormsg, strExpectedErrormsg, "Password error message is not equal.");
		System.out.println("Password Error message displayed succesfully.");
	}

	@Test(enabled = false)
	public void loginToSDFC() {

		String strExpectedDisplay = "Welcome to your free trial should be displayed";
		objdriver.findElement(By.id("username")).sendKeys(strValidUsername);
		objdriver.findElement(By.id("password")).sendKeys(strValidPw);
		objdriver.findElement(By.id("Login")).click();
		Assert.assertTrue(objdriver.getPageSource().contains(strExpectedDisplay),
				"Expected text is not present in the Page.");
		System.out.println("Expectes text is displayed Successfully");

	}

	@Test(invocationCount = 2, enabled = false)
	public void forgotPassword4A() throws MalformedURLException {

		String strExpectPage = "Forgot Your Password | Salesforce";
		objdriver.findElement(By.xpath("//*[@id=\"forgot_password_link\"]")).click();
		String strActTitle = objdriver.getTitle();

		// Check if Forgot password page is landed.
		Assert.assertEquals(strActTitle, strExpectPage, "Failed to navigate 'Forgot password page'");
		System.out.println("Forgot password  page loaded successfully");

		WebDriverWait objWaitU2 = new WebDriverWait(objdriver, Duration.ofSeconds(40));
		objWaitU2.until(ExpectedConditions.visibilityOfElementLocated(By.id("un")));
		objdriver.findElement(By.id("un")).sendKeys(strValidUsername);
		objdriver.findElement(By.id("continue")).click();

		// Check for 'Check your email page' loaded
		String strExpectedEmailpage = "Check Your Email | Salesforce";
		String strActualPage = objdriver.getTitle();
		Assert.assertEquals(strActualPage, strExpectedEmailpage, "Failed to navigate to Check email page");

		// Check for sent email display
		String strActualmsg = objdriver.findElement(By.xpath("//div[@class='message']/p")).getText();
		String strExpectedEmailmsg = "We’ve sent you an email with a link to finish resetting your password.";
		Assert.assertEquals(strActualmsg, strExpectedEmailmsg, "Failed to display resetting password message.");
		// Check for email associated with <username> is sent
		WebDriverWait objWaitU3 = new WebDriverWait(objdriver, Duration.ofSeconds(40));
		objWaitU3.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("resend the email")));
		WebElement objWELlink = objdriver.findElement(By.id("resend-email"));
		String strhref = objWELlink.getAttribute("href");
		/*
		 * URL hrefurl = new URL(strhref); String urlParameters = hrefurl.getQuery();
		 */

		/*
		 * if (urlParameters.contains(strValidUsername)) {
		 * System.out.println("An email associated with the username< " + strhref +
		 * "> has been sent."); } else {
		 * System.out.println("Error with email UserName"); }
		 */

	}

	@Test(enabled = false)
	public void forgotPassword4B() {
		String strErrormsg4B = "Your login attempt has failed. The username or password may be incorrect, or your location or login time may be restricted. Please contact the administrator at your company for help'\"";
		String strinvalidUN = "123";
		String strInvalidpw = "22131";
		// Entering Username
		WebElement objWEUsername = objdriver.findElement(By.id("username"));
		objWEUsername.sendKeys("strinvalidUN");
		// Check if given username is entered in the text
		String strEnteredUN = objWEUsername.getAttribute("value");
		Assert.assertEquals(strEnteredUN, strinvalidUN,
				"UserName field is empty or doesnot match with the entered value");
		// Entering Password
		WebElement objWEpass = objdriver.findElement(By.id("password"));
		objWEpass.sendKeys("Invalidpw");

		// Check if given password is entered in the text
		String strEnteredPW = objWEpass.getAttribute("value");
		Assert.assertEquals(strEnteredPW, strInvalidpw,
				"Password field is empty or doesnot match with the entered value");
		// Click Login button

		objdriver.findElement(By.id("Login")).click();
		// Check if error message matches
		
		

	}

	@Test
	public void checkRememberMe() {
		objdriver.findElement(By.id("username")).sendKeys(strValidUsername);
		objdriver.findElement(By.id("password")).sendKeys(strValidPw);
		// Check RememberMe checkbox
		WebElement objElement = objdriver.findElement(By.id("rememberUn"));
		if (!objElement.isSelected()) {
			objElement.click();
		}
		objdriver.findElement(By.id("Login")).click();
		// Check if Salesforce page is displayed

		String strExpectedTitle = "Home Page ~ Salesforce - Developer Edition";
		JavascriptExecutor jse = (JavascriptExecutor) objdriver;
		String strActualTitleck = (String) jse.executeScript("return document.title");

		Assert.assertEquals(strActualTitleck, strExpectedTitle, "Error in naviagting to Sales Force Home page");

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav-arrow")));
		WebElement objnav = objdriver.findElement(By.id("userNav-arrow"));
		objnav.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Logout']")));
        // Logout click
		objdriver.findElement(By.xpath("//a[@title='Logout']")).click();

		 // Check if Correct user name is displayed

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		JavascriptExecutor jsExec = (JavascriptExecutor) objdriver;
		String strActUsername = (String) jsExec.executeScript("return document.getElementById('username').value");
		Assert.assertEquals(strActUsername, strValidUsername, "Incorrect Username or blank.");

	}

	/*
	 * @AfterTest public void exitBrowser() { objdriver.quit();
	 * System.out.println("Closed All windows."); }
	 */

}
