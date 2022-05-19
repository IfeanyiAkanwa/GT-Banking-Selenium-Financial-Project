package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class LoansAndSavings extends TestBase{
	
	@Test
	
public void navigateToLoansAndSavingsTest() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Loans and Savings");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Dashboard')]")));
		getDriver().findElement(By.xpath("//ul[8]/li/a/span[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Loans and Savings')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(), 'Loans and Savings')]", "Loans and Savings");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Manage your loans and savings, apply for new loans, view investment rates");
		Thread.sleep(500);		
		
	}
	@Test
	public void validateLoansAndSavingsSubModulesTest() throws InterruptedException{

		
		TestUtils.testTitle("To confirm the sub modules on Loans and Savings");
		
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[1]/figure/mat-card", "Quick Credit");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p", "Quick Credit Profiling Request");
		TestUtils.assertSearchTextHasValue("XPATH", "//mat-grid-tile[3]/figure/mat-card/p", "Max Advance");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Quick Cover");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]/figure/mat-card/p", "Spend2Save");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[6]/figure/mat-card/p", "Loan Protect");
		
		Thread.sleep(500);

		//Rater Test
		TestUtils.raterTest("Loans and Savings");

	
	}
	
	@Test
	public static void myInvestmentTab() throws InterruptedException {
		TestUtils.testTitle("Navigate to My Investment");
		TestUtils.assertSearchText("XPATH", "//span/mat-panel-title", "My Investment");
		Thread.sleep(500);
				
		TestUtils.testTitle("To confirm the 'My Investment' button auto hides the Investment section when clicked"  );
		
		//Click on My Investment Panel
		
		getDriver().findElement(By.xpath("//span/mat-panel-title")).click();
		Thread.sleep(500);
		if (getDriver().findElement(By.xpath("//img[@alt='Quick Credit advert banner']")).isDisplayed()) {
			testInfo.get().error("<b> My Investment section is hidden </b>");
		} else {
			testInfo.get().info("<b> My Investment section is not hidden </b>");
		}
		
	}
		

}
