package testCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class AccountStatement extends TestBase {
	
	@Test
	public void navigateToAccountStatementTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Account Statement");
		
		// Click on Proceed to Internet Banking button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clearLoader")));
		getDriver().findElement(By.id("clearLoader")).click();
		Thread.sleep(500);
		
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		getDriver().findElement(By.xpath("//mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Statement')]", "View Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "View Account Statement, download and share");
		Thread.sleep(500);
	}
	
	@Test
	public void accountStatementValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);
		
		// Click on Secure Email
		TestUtils.testTitle("Navigate back to Account Statement view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		getDriver().findElement(By.xpath("//mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Statement')]", "View Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "View Account Statement, download and share");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the first account on the 'Select Account to View Statement' dropdown is selected by default");
		try {
			String acc = getDriver().findElement(By.xpath("//ng-select[@id='undefined']/div")).getText();
			if (!acc.isEmpty()) {
				 testInfo.get().log(Status.INFO, "<b> First Account Number: </b>" + acc + " found");
				    Thread.sleep(500);
			} else {
				testInfo.get().error("No Account is selected by default");
			}
		} catch (Exception e) {
			testInfo.get().error("No Account is selected by default");
		}
		
		
		TestUtils.testTitle("To confirm that the 'Start Date' date picker is visible and date populated is a week from present date");
		try {
			String startDate = getDriver().findElement(By.id("dp")).getAttribute("value");
			if (!startDate.isEmpty()) {
				Assert.assertEquals(TestUtils.getPreviousWeekDateFromCurrentDate(), startDate);
				testInfo.get().log(Status.INFO, "<b> Start Date: </b>" + startDate + " found");
				Thread.sleep(500);
			} else {
				testInfo.get().error("No Date is selected");
			}
		} catch (Exception e) {
			testInfo.get().error("No Date is selected");
		}
	
	
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
	}
	
	@Parameters ("testEnv")
	@Test
	public void sendAccountStatementTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Send Account Statement");
		
		// Select Account 
		getDriver().findElement(By.xpath("//ng-select[@id='undefined']/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[3]")).click();
		Thread.sleep(500);
		
		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("(//button[@type='button'])[6]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[2]/div")).click();
		Thread.sleep(500);
		
		// Select End Date
		getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[4]/td[2]/div")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("Assert total number of Transactions");
		int transCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-accounts/div/div/gtibank-account-statement/div/perfect-scrollbar/div/div/div/mat-card")).size();
		if (TestUtils.isElementPresent("ID", "sendEmail")) {
			testInfo.get().info("Total number of Transactions displayed: <b>" + transCount + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		
//		TestUtils.testTitle("Assert Details of Card");
//		Assertion.assertCardDetails();
		
		// Click on Send Statement button
//		getDriver().findElement(By.id("sendEmail")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
//		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Statement was succesfully sent to your email");
//		//TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
//		Thread.sleep(1000);
//		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
//		Thread.sleep(500);
	}
	 
	
	@Parameters ("testEnv")
	@Test
	public void downloadAccountStatementTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		TestUtils.testTitle("Download Account Statement in .pdf format");
		
		// Select Account 
		getDriver().findElement(By.xpath("//ng-select[@id='undefined']/div/span[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[3]")).click();
		Thread.sleep(500);
		
		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("(//button[@type='button'])[6]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[2]/div")).click();
		Thread.sleep(500);
		
		// Select End Date
		getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[4]/td[2]/div")).click();
		Thread.sleep(500);
		
		TestUtils.testTitle("Assert total number of Transactions");
		int transCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-accounts/div/div/gtibank-account-statement/div/perfect-scrollbar/div/div/div")).size();
		if (TestUtils.isElementPresent("ID", "sendEmail")) {
			testInfo.get().info("Total number of Transactions displayed: <b>" + transCount + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		
//		TestUtils.testTitle("Assert Details of Card");
//		Assertion.assertCardDetails();
		
		// Click on Send Statement button
//		getDriver().findElement(By.id("sendEmail")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
//		TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Statement was succesfully sent to your email");
//		//TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Send Statement')]", "Send Statement");
//		Thread.sleep(1000);
//		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
//		Thread.sleep(500);
	}
}
