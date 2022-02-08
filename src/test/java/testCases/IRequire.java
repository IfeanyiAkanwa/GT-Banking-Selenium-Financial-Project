package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import util.TestBase;
import util.TestUtils;

public class IRequire extends TestBase{

	@Test
	public void navigateToIRequireTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Self Service");
		
		// Click on Self Service
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[9]/li/a")));
		getDriver().findElement(By.xpath("//ul[9]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'I Require')]", "I Require");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Make Request for deliverables and pickup at our specified branch");
		Thread.sleep(500);
	}
	
	@Test
	public void IRequireValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// To confirm that when user clicks on the 'Menu List' button, user is directed back to Self Service view
		TestUtils.testTitle("To confirm that when user clicks on the 'Menu List' button, user is directed back to Self Service view");
		// Click on Menu List
		getDriver().findElement(By.xpath("//div[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Self Service')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Self Service')]", "Self Service");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your profile and accounts settings here");
		Thread.sleep(500);
		
		// Click on IRequire
		TestUtils.testTitle("Navigate back to IRequire view");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[5]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'I Require')]", "I Require");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Make Request for deliverables and pickup at our specified branch");
		Thread.sleep(500);
		
		// To confirm that the IRequire page has 5 tabs at the top of the Request form
		 TestUtils.testTitle("To confirm the sub modules on IRequire menu");
		 TestUtils.assertSearchText("XPATH", "//mat-tab-header/div[2]/div/div/div/div", "Cash Withdrawal");
		 TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[2]/div", "Statement Collection");
		 TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div[3]/div", "Token Request/Collection");
		 TestUtils.assertSearchText("XPATH", "//div[4]/div", "Card Collection");
		 getDriver().findElement(By.xpath("//mat-tab-header/div[3]")).click();
		 Thread.sleep(500);
		 TestUtils.assertSearchText("XPATH", "//div[5]/div", "Cheque Book Collection");
		
		// Rater 
		TestUtils.raterTest("I Require");
		Thread.sleep(1000);
		
		
	}
}
