package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;


public class SelfService extends TestBase {
	Integer WaitTime = 120;
	
	@Test
	public void navigateToSelfServiceModule() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), WaitTime);
		
		TestUtils.testTitle("Navigate to SelfService");
		// Click on SelfService
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[9]/li/a")));
		getDriver().findElement(By.xpath("//ul[9]/li/a")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/a")));
		TestUtils.assertSearchText("XPATH", "//h2/a", "Self Service");
		Thread.sleep(1000);

	};
	
	@Test
	public void SelfServiceSubmodulesCheck() throws InterruptedException {
		TestUtils.testTitle("To assert that all self service submodules exists");
		WebDriverWait wait = new WebDriverWait(getDriver(), WaitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[1]", "Customer Profile");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[2]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]", "Change Password");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[3]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[3]", "Cheques");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[4]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]", "Reference Confirmation");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[5]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]", "I Require");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[6]")));
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[6]", "General Settings");
		Thread.sleep(1000);
		
		TestUtils.raterTest("Self Service");
	};
	
	
}
