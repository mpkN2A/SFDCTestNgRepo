package sdfcTestNGPackage;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserMenuDropdown {
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

	/*
	 * @DataProvider(name = "UserMenuDrpdn") public static Object[][]
	 * dpUsermenuItems(){ return new Object[][]
	 * {{"My Profile"},{"My Settings"},{"Developer Console"},
	 * {"Switch to Lightning Experience"},{"Logout"}}; }
	 */

	@Test(enabled = false)
	public void selectUserMenuDropdown() {

		String[] arrStrUserMenuDd = { "My Profile", "My Settings", "Developer Console",
				"Switch to Lightning Experience", "Logout" };
		int i = 0;

		// Check if UseMenu dropdown is displayed
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNavLabel")));

		WebElement objUserdrp = objdriver.findElement(By.id("userNavLabel"));
		WebElement objUserBtn = objdriver.findElement(By.id("userNav-arrow"));
		Assert.assertTrue(objUserdrp.isDisplayed(), "User Menu dropdown is available");

		// Click UserMenu dropdown
		objUserBtn.click();
		// Check for the given dropdown values
		List<WebElement> lstWEM = objdriver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));

		for (WebElement oWelem : lstWEM) {
			String strdispMenu = oWelem.getText();

			Assert.assertEquals(strdispMenu, arrStrUserMenuDd[i],
					"The item " + arrStrUserMenuDd[i] + " is missing in the UserMenu dropdown");
			i++;
		}

	}

	@Test(enabled = false, dependsOnMethods = "selectUserMenuDropdown")
	public void selectMyProfile() {
		// Click Usermenu dropdown
		WebElement objUserBtnP = objdriver.findElement(By.id("userNav-arrow"));
		objUserBtnP.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='userNav-menuItems']//child::a[1]")));

		WebElement objWEProfile = objdriver.findElement(By.xpath("//div[@id='userNav-menuItems']//child::a[1]"));
		objWEProfile.click();
		// Check if User Profile page is displayed

		String strCheckURL = objdriver.getCurrentUrl();
		Assert.assertTrue(strCheckURL.contains("UserProfilePage"), "User Profile page not displayed");
		// Click on UserProfile Name
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a[@class='contactInfoLaunch editLink']//child::img")));

		objdriver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//child::img")).click();

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("contactInfoContentId")));

		objdriver.switchTo().frame("contactInfoContentId");
		// Check if About Tab and Contact Tab is displayed
		WebElement oWELContactTab = objdriver.findElement(By.id("contactTab"));
		Assert.assertTrue(oWELContactTab.isDisplayed(), "Contact Tab is not displayed");
		WebElement oWELAboutTab = objdriver.findElement(By.id("aboutTab"));
		Assert.assertTrue(oWELAboutTab.isDisplayed(), "About Tab is not displayed");

		// Click on 'About Tab'
		oWELAboutTab.click();
		// Update Last Name
		String strupLastName = "Thor123";
		objdriver.findElement(By.id("lastName")).clear();
		objdriver.findElement(By.id("lastName")).sendKeys(strupLastName);
		objdriver.findElement(By.xpath("//input[@class='zen-btn zen-primaryBtn zen-pas']")).click();
		objdriver.switchTo().defaultContent();
		// Check if last Name is updated
		WebElement weldispLastName = objdriver.findElement(By.xpath("//div[@id='userNav']"));
		String strDispLastName = weldispLastName.getAttribute("title");
		Assert.assertTrue(strDispLastName.contains(strupLastName), "Last Name not updated in the profile.");

		// Click on Post link
		objdriver.findElement(By.xpath("//a[@id='publisherAttachTextPost']")).click();
		WebElement oWELpostFrame = objdriver.findElement(By.xpath("//div[@id='cke_43_contents']//child::iframe"));
		objdriver.switchTo().frame(oWELpostFrame);
		objdriver.findElement(By.xpath("(html/body)")).sendKeys("Post on August-07");
		String strEnteredTxt = objdriver.findElement(By.xpath("(html/body)")).getText();
		objdriver.switchTo().defaultContent();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("publishersharebutton")));

		objdriver.findElement(By.id("publishersharebutton")).click();

		// Check if Entered text is displayed
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='feedwrapper']//descendant::span[@class='feeditemtext cxfeeditemtext']")));

		WebElement welDisplaytxt = objdriver.findElement(
				By.xpath("//div[@id='feedwrapper']//descendant::span[@class='feeditemtext cxfeeditemtext']"));
		String strDisplaytxt = welDisplaytxt.getText();
		Assert.assertEquals(strDisplaytxt, strEnteredTxt);

		// Click on File link
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a[@id='publisherAttachContentPost']/span[1]")));
		objdriver.findElement(By.xpath("//a[@id='publisherAttachContentPost']/span[1]")).click();

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("chatterUploadFileAction")));

		objdriver.findElement(By.id("chatterUploadFileAction")).click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='chatterFile']")));
		objdriver.findElement(By.xpath("//input[@id='chatterFile']"))
				.sendKeys("/Users/athiranihit/Desktop/QA_samplePic.jpeg");
		objdriver.findElement(By.id("publishersharebutton")).click();

		// Add Photo link

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='displayBadge']")));
		objdriver.findElement(By.xpath("//span[@id='displayBadge']")).click();
		// Upload photo

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='uploadPhotoContentId']")));
		WebElement olemPhotoFrame = objdriver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
		objdriver.switchTo().frame(olemPhotoFrame);
		objdriver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']"))
				.sendKeys("/Users/athiranihit/Desktop/QA_samplePic.jpeg");

		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("j_id0:uploadFileForm:uploadBtn")));
		objdriver.findElement(By.name("j_id0:uploadFileForm:uploadBtn")).click();
		// objdriver.switchTo().defaultContent();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='j_id0:j_id7:save']")));
		objdriver.findElement(By.xpath("//input[@id='j_id0:j_id7:save']")).click();

	}

	@Test(enabled = false)
	public void selectMySettings() {

		// Click Usermenu dropdown
		String strExpecTitle = "Setup";
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav-arrow")));
		WebElement objUserBtnP = objdriver.findElement(By.id("userNav-arrow"));
		objUserBtnP.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='userNav-menuItems']//child::a[2]")));

		WebElement objWEProfile = objdriver.findElement(By.xpath("//div[@id='userNav-menuItems']//child::a[2]"));
		objWEProfile.click();

		String strChkUrl = objdriver.getCurrentUrl();
		Assert.assertTrue(strChkUrl.contains(strExpecTitle), "My settings page not displayed.");

		///////////// Click on Personal link Starts
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='PersonalInfo']//descendant::span[@id='PersonalInfo_font']")));
		objdriver.findElement(By.xpath("//div[@id='PersonalInfo']//descendant::span[@id='PersonalInfo_font']")).click();

		// Click on Login history
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='PersonalInfo']//descendant::span[@id='LoginHistory_font']")));
		objdriver.findElement(By.xpath("//div[@id='PersonalInfo']//descendant::span[@id='LoginHistory_font']")).click();

		// Check if LoginHistory page is displayed
		String strExpLogHislnk = "Login History ~ Salesforce - Developer Edition";
		String ActEXpLogHislnk = objdriver.getTitle();
		Assert.assertEquals(ActEXpLogHislnk, strExpLogHislnk);

		// Click Download link
		objdriver
				.findElement(By.xpath(
						"//div[@id='RelatedUserLoginHistoryList_body']//child::div[@class='pShowMore']//child::a[1]"))
				.click();

		// Check if .csv file is downloaded
		String strDownloadPath = "/Users/athiranihit/Downloads";
		File oFilePath = new File(strDownloadPath);
		File[] oFilelist = oFilePath.listFiles();
		File lastModifiedFile = oFilelist[0];
		for (int i = 1; i < oFilelist.length; i++) {
			if (lastModifiedFile.lastModified() < oFilelist[i].lastModified()) {
				lastModifiedFile = oFilelist[i];
			}
		}
		String strLastAddedFile = lastModifiedFile.getName();
		Assert.assertTrue(strLastAddedFile.startsWith("LoginHistory") && strLastAddedFile.contains(".csv"));
		System.out.println("Login History file with the extensions of .csv downloaded Succesfully");
		///////////// Click on Personal link Ends.

		///////////// Click on Display&Layout Starts

		///////////// Click on Display&Layout link
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='DisplayAndLayout_font']")));
		objdriver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']")).click();
		// Click on Customize my Tab

		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='DisplayAndLayout_child']//descendant::span[@id='CustomizeTabs_font']")));
		objdriver
				.findElement(
						By.xpath("//div[@id='DisplayAndLayout_child']//descendant::span[@id='CustomizeTabs_font']"))
				.click();

		// Selecting Salesforce Chatter from dropdown
		Select sldrpdnCustPage = new Select(objdriver.findElement(By.xpath("//select[@id='p4']")));
		sldrpdnCustPage.selectByVisibleText("Salesforce Chatter");

		Select slAvailTab = new Select(objdriver.findElement(By.xpath("//select[@id='duel_select_0']")));
		slAvailTab.selectByVisibleText("Reports");
		// Click Add button
		objdriver.findElement(By.xpath("//a[@id='duel_select_0_right']//child::img[1]")).click();
		// Check if selected value is added to Selected Tab
		// Select slSelectTab = new
		// Select(objdriver.findElement(By.xpath("//select[@id='duel_select_1']")));
		String strisReport = objdriver.findElement(By.xpath("//select[@id='duel_select_1']//option[@value='report']"))
				.getAttribute("value");
		Assert.assertTrue(strisReport.equals("report"), "The selected value Report is not added in the Selected Tab.");
		System.out.println("Report is added to Selected Tab");
		// Click Save button
		objdriver.findElement(By.xpath("//td[@id='bottomButtonRow']/input[@title='Save']")).click();

		// Check if Report tab is added in the Salesforce home page
		WebElement welTabBar = objdriver.findElement(By.xpath("//li[@id='report_Tab']/a[1]"));
		Assert.assertEquals(welTabBar.isDisplayed(), "Report Tab is not displayed in the Sales force Home Tab Bar.");

		// Check if Report is added
		objdriver.findElement(By.id("tsidButton")).click();
		// Select Sales page and check if report tab is visible
		objdriver.findElement(By.xpath("//div[@id='tsid-menuItems']//child::a[1]"));
		WebElement welSales = objdriver.findElement(By.xpath("//ul[@id='tabBar']//li[@id='report_Tab']"));
		Assert.assertEquals(welSales.isDisplayed(), "Report Tab is not displayed in Sales Home Tab Bar.");

		// Check in 'Marketing' Page
		objdriver.findElement(By.xpath("//div[@id='tsid-menuItems']//child::a[3]"));
		WebElement welMarketing = objdriver.findElement(By.xpath("//ul[@id='tabBar']//child::li[@id='report_Tab']"));
		Assert.assertEquals(welMarketing.isDisplayed(), "Report Tab is not displayed in the Marketing page home tab.");

		// Check in 'Sales Force chatter' Page
		objdriver.findElement(By.xpath("//div[@id='tsid-menuItems']//child::a[7]"));
		WebElement welSFChatter = objdriver.findElement(By.xpath("//ul[@id='tabBar']//child::li[@id='report_Tab']"));
		Assert.assertEquals(welSFChatter.isDisplayed(), "Report Tab is not displayed in the SF Chatter page home tab.");

		///////////// Click on Display&Layout Ends

	}

	@DataProvider(name = "EmailSettingData")
	public static Object[][] EmailNameandAddress() {

		return new Object[][] { { "MKP", "monapriya2986@gmail.com" } };

	}

	@Test(dataProvider = "EmailSettingData", enabled = false)
	public void mySettingsEmailPage(String strUserName, String strEmailAddress) {

		// Click Usermenu dropdown
		String strExpecTitle = "Setup";
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav-arrow")));
		WebElement objUserBtnP = objdriver.findElement(By.id("userNav-arrow"));
		objUserBtnP.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='userNav-menuItems']//child::a[2]")));

		WebElement objWEProfile = objdriver.findElement(By.xpath("//div[@id='userNav-menuItems']//child::a[2]"));
		objWEProfile.click();

		String strChkUrl = objdriver.getCurrentUrl();
		Assert.assertTrue(strChkUrl.contains(strExpecTitle), "My settings page not displayed.");

		// Clcik email and email settings
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='EmailSetup_font']")));

		objdriver.findElement(By.xpath("//span[@id='EmailSetup_font']")).click();
		objdriver.findElement(By.xpath("//a[@id='EmailSettings_font']")).click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("sender_name")));
		objdriver.findElement(By.id("sender_name")).clear();
		objdriver.findElement(By.id("sender_name")).sendKeys(strUserName);
		objdriver.findElement(By.id("sender_email")).clear();
		objdriver.findElement(By.id("sender_email")).sendKeys(strEmailAddress);
		WebElement welChkbox = objdriver
				.findElement(By.xpath("//div[@id='useremailSection']//descendant::input[@id='auto_bcc1']"));
		if (!welChkbox.isSelected()) {
			welChkbox.click();

		}
		objdriver.findElement(By.xpath("//td[@id='bottomButtonRow']//child::input[@name='save']")).click();
		WebElement ol = objdriver.findElement(By.xpath("//div[@id='meSaveCompleteMessage']//descendant::div[1]"));
		String strDisplmsg = ol.getText();
		String strExpec = "Your settings have been successfully saved.";
		Assert.assertEquals(strDisplmsg, strExpec);
		System.out.println("Email setting Successfully updated");
	}

	@Test(enabled = false)
	public void calendarsandRemainders() {
		// Click Usermenu dropdown
		String strExpecTitle = "Setup";
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav-arrow")));
		WebElement objUserBtnP = objdriver.findElement(By.id("userNav-arrow"));
		objUserBtnP.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='userNav-menuItems']//child::a[2]")));

		WebElement objWEProfile = objdriver.findElement(By.xpath("//div[@id='userNav-menuItems']//child::a[2]"));
		objWEProfile.click();

		String strChkUrl = objdriver.getCurrentUrl();
		Assert.assertTrue(strChkUrl.contains(strExpecTitle), "My settings page not displayed.");

		// Clcik Calndars & Reminders
		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='CalendarAndReminders_font']")));

		objdriver.findElement(By.xpath("//span[@id='CalendarAndReminders_font']")).click();
		// Click on Activity reminders
		objdriver.findElement(By.xpath("//a[@id='Reminders_font']")).click();
		// Save button click
		objdriver.findElement(By.xpath("//td[@id='bottomButtonRow']//child::input[@name='save']")).click();
		// Check for popup window
		String strSampleEventpopupW = objdriver.getWindowHandle();
		try {
			objdriver.switchTo().window("strSampleEventpopupW");
			objdriver.switchTo().defaultContent();
		} catch (NoSuchWindowException e) {
			System.out.println("ERROR :Sample event pop window is NOT dispayed.");

		}

	}

	@Test
	public void developerConsole() {
		// Click Usermenu dropdown
		WebElement objUserBtnP = objdriver.findElement(By.id("userNav-arrow"));
		objUserBtnP.click();

		new WebDriverWait(objdriver, Duration.ofSeconds(40)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='userNav-menuItems']//child::a[3]")));

		WebElement objWEDevConsole = objdriver.findElement(By.xpath("//div[@id='userNav-menuItems']//child::a[3]"));
		objWEDevConsole.click();
		// Check if Force.com Developer window is opened
		String strHandle = objdriver.getWindowHandle();
		try {
			// objdriver.switchTo().alert();
			objdriver.switchTo().window(strHandle);
			System.out.println("Force.com Developer window is opened");
		} catch (NoSuchWindowException e) {	
			System.out.println("ERROR :SForce.com Developer window is NOT opened");
		}

		objdriver.switchTo().defaultContent();

	}

	@Test(enabled=false)
	public void logout() {
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav-arrow")));
		WebElement objnav = objdriver.findElement(By.id("userNav-arrow"));
		objnav.click();
		new WebDriverWait(objdriver, Duration.ofSeconds(40))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Logout']")));
		// Logout click
		objdriver.findElement(By.xpath("//a[@title='Logout']")).click();

		String strActualpageURL = objdriver.getCurrentUrl();
		String strExpectPageURL = "https://login.salesforce.com/";

		Assert.assertEquals(strActualpageURL, strExpectPageURL, "Error in Navigation to Login Page.");
		System.out.println("Logged out Successfully");

	}

}
