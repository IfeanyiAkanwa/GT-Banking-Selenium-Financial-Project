package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class LandingPage extends TestBase{
	
	@Parameters ("testEnv")
	@Test
	public void navigateToLandingPageTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Landing Page");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h1", "Welcome! Vihiior,mercy");
		
		// To confirm that GtBank Logo is displayed on the Landing Page
		Assertion.logoDisplayTest();
		
		// Rater for Landing page
		TestUtils.raterTest("Landing Page");
	}
	
	@Test
	public void navigateToDashboardTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Dashboard");
		
		// Click on Proceed to Internet Banking button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clearLoader")));
		getDriver().findElement(By.id("clearLoader")).click();
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Dashboard')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Dashboard')]", "Dashboard");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Quickly perform task from the dashboard or view reports.");
	}

}
