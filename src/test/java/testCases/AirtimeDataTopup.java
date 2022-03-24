package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class AirtimeDataTopup extends TestBase{

	@Test
	public void navigateToAirtimeDataTopupTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Airtime/Data Topup");
		
		// Click on Airtime/Data Topup
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Dashboard')]")));
		getDriver().findElement(By.xpath("//ul[5]/li/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Airtime/Data Topup')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Airtime/Data Topup')]", "Airtime/Data Topup");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Topup your airtime or data bundle");
		Thread.sleep(500);
	}

	@Test
	 public void airtimeDataSubModuleCheckTest() throws InterruptedException {
		 
		 TestUtils.testTitle("To confirm the sub modules on Airtime/Data Topup menu");
		 TestUtils.assertSearchText("XPATH", "//mat-card/p", "MTN Nigeria");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p", "AIRTEL Nigeria");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[3]/figure/mat-card/p", "9Mobile");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Globacom Nigeria");
		 Thread.sleep(500);
		 
		 // Rater
		 TestUtils.raterTest("Accounts");
	}
	
	@Test
	public void frequentAirtimeTopupTest() throws InterruptedException {
		
		TestUtils.testTitle("Navigate to Frequent Airtime/topup");
		TestUtils.assertSearchText("XPATH", "//span/mat-panel-title", "Frequent Airtime/Top-up");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Frequent Airtime/Topup section has a Airtime Topup Button");
		TestUtils.assertSearchText("XPATH", "//mat-tab-header/div[2]/div/div/div/div", "Airtime Topup");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Frequent Airtime/Topup section has a Data Topup Button");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[2]/div", "Data Topup");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that all previous airtime topup records are displayed when user cilcks on Airtime Topup button");
		try {
			if (getDriver().findElement(By.xpath("//div/mat-grid-list/div/mat-grid-tile/figure/mat-card")).isDisplayed()) {
				testInfo.get().info("Previous Airtime records are displayed");
				
				TestUtils.testTitle("Assert total number of records");
				int cardCount = getDriver().findElements(By.xpath("//div/mat-grid-list/div/mat-grid-tile/figure/mat-card")).size();
				if (TestUtils.isElementPresent("XPATH", "//div[2]/div/div/div[2]")) {
					testInfo.get().info("Total number of Records displayed: <b>" + cardCount + "</b>");
				} else {
					testInfo.get().error("Table is empty.");
				}
				
			} else {
				Thread.sleep(2000);
				testInfo.get().info("No records found for frequent topup.");
			}
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//app-gtibank-frequent-topup/div/p", "No records found for frequent topup.");
			Thread.sleep(500);
		}
	
		TestUtils.testTitle("To confirm that all previous Data topup records are displayed when user cilcks on Data Topup button");
		// Click on Data Topup
		getDriver().findElement(By.xpath("//div[2]/div/div/div[2]")).click();
		Thread.sleep(500);
		try {
			if (getDriver().findElement(By.xpath("//div/mat-grid-list/div/mat-grid-tile/figure/mat-card")).isDisplayed()) {
				testInfo.get().info("Previous Airtime records are displayed");
				
				TestUtils.testTitle("Assert total number of records");
				int cardCount = getDriver().findElements(By.xpath("//div/mat-grid-list/div/mat-grid-tile/figure/mat-card")).size();
				if (TestUtils.isElementPresent("XPATH", "//div[2]/div/div/div[2]")) {
					testInfo.get().info("Total number of Records displayed: <b>" + cardCount + "</b>");
				} else {
					testInfo.get().error("Table is empty.");
				}
				
			} else {
				Thread.sleep(2000);
				testInfo.get().info("No records found for frequent topup.");
			}
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//app-gtibank-frequent-topup/div/p", "No records found for frequent topup.");
			Thread.sleep(500);
		}
	}
	
}
