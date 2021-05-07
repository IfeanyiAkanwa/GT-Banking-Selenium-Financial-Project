package testCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.TestBase;
import util.TestUtils;

public class StatementToThirdParty extends TestBase{

	@Test
	public void navigateToStatementToThirdPartyTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Statement To Third Party");
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(1000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[2]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[2]/figure/mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Generate Statement and send to third party");
		Thread.sleep(500);
	}
	
	@Test
	public void statementToThirdPartyValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);
		
		// Click on Statement To Third Party
		TestUtils.testTitle("Navigate back to Statement To Third Party");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[2]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[2]/figure/mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Generate Statement and send to third party");
		Thread.sleep(500);
		
		// To confirm that Enter Country drop down populates lists of country
		testInfo.get().info("<b> Embassy Section</b>");
		TestUtils.testTitle("To confirm that Enter Country drop down populates lists of country");
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div", "United Kingdom");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Australia");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "Austria");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		
		// To confirm that Statement Account drop-down displays list of account holder's existing accounts with balance
		TestUtils.testTitle("To confirm that Statement Account drop-down displays list of account holder's existing accounts with balance");
		getDriver().findElement(By.xpath("//ng-select/div/span[2]")).click();
		Thread.sleep(500);
		int cardCount = getDriver().findElements(By.xpath("//ng-dropdown-panel/div/div[2]/div")).size();
		if (TestUtils.isElementPresent("XPATH", "//label[2]")) {
			testInfo.get().info("Total number of Accounts displayed: <b>" + cardCount + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		getDriver().findElement(By.xpath("//ng-select/div/span[2]")).click();
		Thread.sleep(500);
		
		// To confirm that the default start date is 6 months from the present date
		TestUtils.testTitle("To confirm that the default start date is 6 months from the present date");
		try {
			String startDate = getDriver().findElement(By.id("dp")).getAttribute("value");
			if (!startDate.isEmpty()) {
				Assert.assertEquals(TestUtils.getSixMonthsAgoDateTest(), startDate);
				testInfo.get().log(Status.INFO, "<b> Start Date: </b>" + startDate + " found");
				Thread.sleep(500);
			} else {
				testInfo.get().error("No Date is selected");
			}
		} catch (Exception e) {
			testInfo.get().error("No Date is selected");
		}
		
		// To confirm that the 'End Date' date picker is visible and date populated is present date
		TestUtils.testTitle("To confirm that the 'End Date' date picker is visible and date populated is present date");
		try {
			String endDate = getDriver().findElement(By.xpath("(//input[@id='dp'])[2]")).getAttribute("value");
			if (!endDate.isEmpty()) {
				Assert.assertEquals(TestUtils.getTodaysDate(), endDate);
				testInfo.get().log(Status.INFO, "<b> End Date: </b>" + endDate + " found");
				Thread.sleep(500);
			} else {
				testInfo.get().error("No Date is selected");
			}
		} catch (Exception e) {
			testInfo.get().error("No Date is selected");
		}
		
		// To confirm that Account to Debit drop-down displays list of existing accounts with balance
		TestUtils.testTitle("To confirm that Account to Debit drop-down displays list of existing accounts with balance");
		TestUtils.scrollToElement("XPATH", "//div[8]/div/button/span");
		getDriver().findElement(By.xpath("//div[5]/gtibank-accounts-typeahead/div/ng-select/div/span[2]")).click();
		Thread.sleep(500);
		int cardCount1 = getDriver().findElements(By.xpath("//ng-dropdown-panel/div/div[2]/div")).size();
		if (TestUtils.isElementPresent("XPATH", "//label[2]")) {
			testInfo.get().info("Total number of Accounts displayed: <b>" + cardCount1 + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		getDriver().findElement(By.xpath("//div[5]/gtibank-accounts-typeahead/div/ng-select/div/span[2]")).click();
		Thread.sleep(500);
		
		// To confirm that Select Role drop-down displays three options (Applicant, Guarantor and Sponsor)
		TestUtils.testTitle("To confirm that Select Role drop-down displays three options (Applicant, Guarantor and Sponsor)");
		getDriver().findElement(By.xpath("//div[6]/ng-select/div/span")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div", "Applicant");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[2]", "Sponsor");
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[3]", "Guarantor");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[6]/ng-select/div/span")).click();
		Thread.sleep(500);
		
		// To confirm that Send Statement Button is disabled when all fields are empty
		TestUtils.testTitle("To confirm that Send Statement Button is disabled when all fields are empty");
		try {
			if (!getDriver().findElement(By.id("validate")).isEnabled()) {
				testInfo.get().info("<b> Send Statement button is disabled</b>");
			} else {
				testInfo.get().error("<b> Send Statement button is enabled</b>");
			}
		} catch (Exception e) {
			testInfo.get().error("<b> Send Statement button is enabled</b>");
		}
		
		// To confirm that Enter Country drop down populates lists of country
		testInfo.get().info("<b> Other Third Parties Section </b>");
		TestUtils.testTitle("To confirm that Enter name of Third Party drop down populates lists of Third Parties");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//label[2]");
		getDriver().findElement(By.xpath("//label[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		
		List<WebElement> resultList = getDriver().findElements(By.xpath("//ng-dropdown-panel/div/div[2]/div"));
        for (WebElement resultItem : resultList){
           String tabname = resultItem.getText();
           testInfo.get().info("<b>"+ tabname + "</b>");
        }
		
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		
		// Rater
		TestUtils.raterTest("Statement To Third Party");
		Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
	@Test
	public void sendStatementTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("StatementToThirdParty");

		String applicantName = (String) envs.get("applicantName");
		
		// Click on Embassy
		getDriver().findElement(By.xpath("//label")).click();
		Thread.sleep(500);
		
		// Select Country
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		String embassy = getDriver().findElement(By.xpath("//ng-select/div")).getText();
		TestUtils.testTitle("Send Statement to Embassy: " + embassy);
		
		// Select Statement Account
		getDriver().findElement(By.xpath("//ng-select/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		// Select Start Date
		getDriver().findElement(By.xpath("//mat-datepicker-toggle/button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[7]/div")).click();
		Thread.sleep(500);

		// Select End Date
		getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div/div[4]/mat-datepicker-toggle/button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[6]/div")).click();
		Thread.sleep(1000);
		
		// Select Account to Debit
		getDriver().findElement(By.xpath("//div[5]/gtibank-accounts-typeahead/div/ng-select/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[8]/div/button/span");
		
		// Select Role
		getDriver().findElement(By.xpath("//div[6]/ng-select/div/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		
		// Enter Applicants name
		getDriver().findElement(By.id("applicant")).clear();
		getDriver().findElement(By.id("applicant")).sendKeys(applicantName);
		Thread.sleep(500);
		
		// Submit
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ibank-notifications/div/div/div/div/div/p")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p", "Please note that you will be charged for this transaction");
		Thread.sleep(500);
		
		// Continue button
		getDriver().findElement(By.xpath("//div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Token Confirmation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-token-confirmation-modal/div/div/div/p")));
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(1000);
		
		// Click on Other Third Parties
		getDriver().findElement(By.xpath("//label[2]")).click();
		Thread.sleep(500);
		
		// Select Third Party
		getDriver().findElement(By.xpath("//ng-select/div/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(500);

		String thirdParty = getDriver().findElement(By.xpath("//ng-select/div")).getText();
		TestUtils.testTitle("Send Statement to Other Third Parties: " + thirdParty);

		// Select Statement Account
		getDriver().findElement(By.xpath("//gtibank-accounts-typeahead/div/ng-select/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(500);

		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[7]/div")).click();
		Thread.sleep(500);

		// Select End Date
		getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div/div[4]/mat-datepicker-toggle/button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[6]/div")).click();
		Thread.sleep(1000);

		// Select Account to Debit
		getDriver().findElement(By.xpath("//div[5]/gtibank-accounts-typeahead/div/ng-select/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[8]/div/button/span");

		// Select Role
		getDriver().findElement(By.xpath("//div[6]/ng-select/div")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(500);

		// Enter Applicants name
		getDriver().findElement(By.id("applicant")).clear();
		getDriver().findElement(By.id("applicant")).sendKeys(applicantName);
		Thread.sleep(500);

		// Submit
		getDriver().findElement(By.id("validate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ibank-notifications/div/div/div/div/div/p")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div/p",	"Please note that you will be charged for this transaction");
		Thread.sleep(500);

		// Continue button
		getDriver().findElement(By.xpath("//div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		TestUtils.assertSearchText("XPATH", "//h3", "Token Confirmation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-token-confirmation-modal/div/div/div/p")));
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
		
	}
}
