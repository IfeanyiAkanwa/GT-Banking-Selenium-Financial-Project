package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class Accounts extends TestBase{
	
	@Test
	public void navigateToAccountsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Accounts");
		
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Dashboard')]")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'My Accounts')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'My Accounts')]", "My Accounts");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your accounts, view balance and do more.");
		Thread.sleep(500);
	}

	@Test
	 public void accountSubModuleCheckTest() throws InterruptedException {
		 
		 TestUtils.testTitle("To confirm the sub modules on Accounts menu");
		 TestUtils.assertSearchText("XPATH", "//mat-card/p", "Account Statement");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p", "Transaction History");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[3]/figure/mat-card/p", "Statement to Third-Party");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Open Domiciliary Account");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]/figure/mat-card/p", "Open Additional Account");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[6]/figure/mat-card/p", "Update & Upgrade Account");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[7]/figure/mat-card/p", "Profile & Unprofile Account");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[8]/figure/mat-card/p", "Secure Email");
		 TestUtils.assertSearchText("XPATH", "//mat-grid-tile[9]/figure/mat-card/p", "Sachet Banking");
		 Thread.sleep(500);
		 
		 // Rater
		 TestUtils.raterTest("Accounts");
		 
		 // Account Officer
		 TestUtils.accOfficerValidationTest();
	
	}
}
