package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class ProfileUnprofileAccount extends TestBase{
	
	@Test
	public void naviagteToProfileUnprofileAccountTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Profile and Unprofile Accounts");
	
		// Click on Accounts
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[2]/li/a")));
		getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[7]/figure/mat-card/p")));
		getDriver().findElement(By.xpath("//mat-grid-tile[7]/figure/mat-card")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Accounts Profiling')]", "Accounts Profiling");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Profile and Unprofile Accounts");
		Thread.sleep(500);
	}

	@Test
	public void assertNumberOfAccountsTest() throws InterruptedException {
		
		TestUtils.testTitle("Assert total number of accounts");
		int accCount = getDriver().findElements(By.xpath("//*[@id='pcoded']/div[2]/div[3]/div/div/div/div[1]/div/gtibank-accounts/div/div/gtibank-profile-accounts/div/div/div/div/form/div/perfect-scrollbar/div/div/mat-card")).size();
		if (TestUtils.isElementPresent("XPATH", "//h1")) {
			testInfo.get().info("Total number of Accounts displayed: <b>" + accCount + "</b>");
		} else {
			testInfo.get().error("Table is empty.");
		}
		
		// Rater
		TestUtils.raterTest("Profile and Unprofile Accounts");
	}
	
	@Test
	public void addAccountsToProfileList() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		String acc = getDriver().findElement(By.xpath("//p[2]")).getText();
		TestUtils.testTitle("Add Account to Profile List: " + acc);
		
		// Click on checkbox
		getDriver().findElement(By.xpath("//mat-card[2]/div[2]/mat-checkbox/label/div")).click();
		Thread.sleep(500);
		
		// Click on Save Changes
		getDriver().findElement(By.xpath("//mat-card/div/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ibank-notifications/div/div/div/div/div")));
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div/div/div", "Success");
		Thread.sleep(500);
		
		// Click on Close button
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
		
		TestUtils.accOfficerValidationTest();
	}
	
}
