package testCases;

import java.text.ParseException;

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
	public void accountStatementValidationTest() throws InterruptedException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to main menu of account module");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);
		
		// Click on Account Statement
		TestUtils.testTitle("Navigate back to Account Statement view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card/p")));
		getDriver().findElement(By.xpath("//mat-card/p")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Statement')]", "View Statement");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "View Account Statement, download and share");
		Thread.sleep(500);
		
		// To confirm that the first account on the 'Select Account to View Statement' dropdown is selected by default
		/*
		 * TestUtils.
		 * testTitle("To confirm that the first account on the 'Select Account to View Statement' dropdown is selected by default"
		 * ); try { String acc =
		 * getDriver().findElement(By.xpath("//input[@id='account']")).getText(); if
		 * (!acc.isEmpty()) { testInfo.get().log(Status.INFO,
		 * "<b> First Account Number: </b>" + acc + " found"); Thread.sleep(500); } else
		 * { testInfo.get().error("No Account is selected by default"); } } catch
		 * (Exception e) { testInfo.get().error("No Account is selected by default"); }
		 */
		
		// Click on Select account to view Statement
		getDriver().findElement(By.xpath("//input[@id='account']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div/div[1]")).click();
		Thread.sleep(500);
		
		// To confirm that the 'Start Date' date picker is visible and date populated is a week from present date
		TestUtils.testTitle("To confirm that the 'Start Date' date picker is visible and date populated is a week from present date");
		
			String startDate = getDriver().findElement(By.name("startDate")).getAttribute("value");
			if (!startDate.isEmpty()) {
				Assert.assertEquals(TestUtils.getPreviousWeekDateFromCurrentDate(), startDate);
				testInfo.get().log(Status.INFO, "<b> Start Date: </b>" + startDate + " found");
				Thread.sleep(500);
			} else {
				testInfo.get().error("No Date is selected");
			}
		
	
	    // To confirm that the 'End Date' date picker is visible and date populated is present date
		TestUtils.testTitle("To confirm that the 'End Date' date picker is visible and date populated is present date");
		try {
			String endDate = getDriver().findElement(By.name("endDate")).getAttribute("value");
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
		
		// Rater 
		TestUtils.raterTest("Account Statement");
		Thread.sleep(1000);
		
		// Select Account
		String acc = getDriver().findElement(By.xpath("//input[@id='account']")).getText();
		testInfo.get().log(Status.INFO, "<b> Account to view Statement: </b>" + acc + " found");
		//getDriver().findElement(By.xpath("//div[2]/div[4]")).click();
		Thread.sleep(1000);

		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[6]/td[4]/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[2]/td[3]/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[2]/td[5]/div")).click();
		Thread.sleep(500);

		// Select End Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[6]/td[4]/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[2]/td[3]/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[3]/td[4]/div")).click();
		Thread.sleep(1000);
		
		String startDate1 = getDriver().findElement(By.name("startDate")).getAttribute("value");
		String endDate1 = getDriver().findElement(By.name("endDate")).getAttribute("value");
		
		TestUtils.testTitle("Assert total number of Transactions returned within Start Date: (" + startDate1 + ")  and End Date: (" + endDate1 + ")");
		 try {
			 int accCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-accounts/div/div/gtibank-account-statement/div/perfect-scrollbar/div/div/div")).size();
				if (TestUtils.isElementPresent("XPATH", "//h1")) {
					testInfo.get().info("Total number of Accounts displayed: <b>" + accCount + "</b>");
				} else {
					testInfo.get().error("Table is empty.");
				}
		} catch (Exception e) {
			testInfo.get().info("No statement to display");
			TestUtils.assertSearchText("XPATH", "//gtibank-account-statement/div/div/p[2]", "Select another account or change transaction period.");
			
			// To confirm the 'Download' button and 'Send Statement' button is not visible when the account has no statement
			TestUtils.testTitle("To confirm the 'Download' button and 'Send Statement' button is not visible when the account has no statement");
		
			// Send Statement button
			if (TestUtils.isElementPresent("ID", "sendEmail")) {
				testInfo.get().error("Send Statement button is visible");
				Thread.sleep(500);
			} else {
				testInfo.get().info("<b> Send Statement button is not visible </b>");
			}

			// Download button
			if (TestUtils.isElementPresent("XPATH", "//span/button")) {
				testInfo.get().error("Download button is visible");
				Thread.sleep(500);
			} else {
				testInfo.get().info("<b> Download button is not visible </b>");
			}
		}
		
	}
	
	@Parameters ("testEnv")
	@Test
	public void sendAccountStatementTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Select Account 
		TestUtils.testTitle("Select Account to view Statement");
		getDriver().findElement(By.xpath("//input[@id='account']")).click();
		Thread.sleep(500);
		String acc = getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).getText();
		testInfo.get().log(Status.INFO, "<b> Account to view Statement: </b>" + acc+ " found");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
		Thread.sleep(1000);
		
		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("(//button[@type='button'])[6]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[2]/div")).click();
		Thread.sleep(500);
		
		
		  // Select End Date
		  //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("svg.mat-datepicker-toggle-default-icon.ng-star-inserted"))).click();
		  //getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		  getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div")).click();
		  Thread.sleep(1000);
		  getDriver().findElement(By.xpath("//tr[4]/td[2]/div")).click();
		  Thread.sleep(1000);
		  //getDriver().findElement(By.xpath("//td[2]/div")).click();
		  //Thread.sleep(1000);
		 
		String startDate = getDriver().findElement(By.name("startDate")).getAttribute("value");
		String endDate = getDriver().findElement(By.name("endDate")).getAttribute("value");
		
		TestUtils.testTitle("Assert total number of Transactions returned within Start Date: (" + startDate + ")  and End Date: (" + endDate + ")");
		int transCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-accounts/div/div/gtibank-account-statement/div/perfect-scrollbar/div/div/div")).size();
		if (TestUtils.isElementPresent("ID", "sendEmail")) {
			testInfo.get().info("Total number of Transactions displayed: <b>" + transCount + "</b>");
			
			TestUtils.testTitle("Assert Details of a particular Transaction");
			Assertion.assertAccountStatementTransactionDetails();
			
			// Click on Send Statement button
			TestUtils.testTitle("Send Account Statement");
			getDriver().findElement(By.id("sendEmail")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gtb-notification/mat-card/p")));
			TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/div/p", "Send Statement");
			TestUtils.assertSearchText("XPATH", "//gtb-notification/mat-card/p", "Statement was succesfully sent to your email");
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
			Thread.sleep(500);
		} else {
			testInfo.get().error("Table is empty.");
		}
		
	}
	 
	
	@Parameters ("testEnv")
	@Test
	public void downloadAccountStatementTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Select Account 
		TestUtils.testTitle("Select Account to view Statement");
		getDriver().findElement(By.xpath("//input[@id='account']")).click();
		Thread.sleep(500);
		String acc = getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).getText();
		testInfo.get().log(Status.INFO, "<b> Account to view Statement: </b>" + acc+ " found");
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[1]")).click();
		Thread.sleep(500);
		
		// Select Start Date
		getDriver().findElement(By.cssSelector("svg.mat-datepicker-toggle-default-icon.ng-star-inserted")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//mat-calendar-header/div/div/button[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[2]/div")).click();
		Thread.sleep(500);
		
		// Select End Date
		getDriver().findElement(By.xpath("//gtibank-gt-datepicker-input[2]/div/mat-form-field/div/div")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//tr[4]/td[2]/div")).click();
		Thread.sleep(500);
		
		String startDate = getDriver().findElement(By.name("startDate")).getAttribute("value");
		String endDate = getDriver().findElement(By.name("endDate")).getAttribute("value");
		
		TestUtils.testTitle("Assert total number of Transactions returned within Start Date: (" + startDate + ")  and End Date: (" + endDate + ")");
		int transCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div/div/gtibank-accounts/div/div/gtibank-account-statement/div/perfect-scrollbar/div/div/div")).size();
		if (TestUtils.isElementPresent("ID", "sendEmail")) {
			testInfo.get().info("Total number of Transactions displayed: <b>" + transCount + "</b>");
			
			TestUtils.testTitle("Assert Details of a particular Transaction");
			Assertion.assertAccountStatementTransactionDetails();
			
			// Click on Download button
			TestUtils.testTitle("Download Account Statement in .pdf format");
			getDriver().findElement(By.xpath("//span/button")).click();
			Thread.sleep(500);
			
			// select pdf
			getDriver().findElement(By.xpath("//div[2]/div/div/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sendEmail")));
			testInfo.get().info("Statement was successfully downloaded");
			Thread.sleep(500);
			
			// Click on download button
			TestUtils.testTitle("Download Account Statement in EXCEL format");
			getDriver().findElement(By.xpath("//span/button")).click();
			Thread.sleep(500);
			
			// select excel
			getDriver().findElement(By.xpath("//div[2]/div/div/div/button[2]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sendEmail")));
			testInfo.get().info("Statement was successfully downloaded");
			Thread.sleep(500);
		} else {
			testInfo.get().error("Table is empty.");
		}
		
		// Account Officer
		TestUtils.accOfficerValidationTest();
	}
}


