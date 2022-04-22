package testCases;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestBase;
import util.TestUtils;

public class Transfers extends TestBase{
	
	@Test
	public void navigateToTransfersTest() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate to Transfers");
		
		//Click on Transfers
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Dashboard')]")));
		getDriver().findElement(By.xpath("//ul[3]/li/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Transfers')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Transfers')]", "Transfers");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "Transfer money between your accounts or other accounts.");
		Thread.sleep(500);		
	}
	
	@Test
	public void transfersSubModuleCheckTest() throws InterruptedException {
		TestUtils.testTitle("To confirm the sub modules on Transfers menu");
		TestUtils.assertSearchText("XPATH", "//mat-card/p", "Transfer to GTBank");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[2]/figure/mat-card/p", "Transfer to Other Banks");
		TestUtils.assertSearchTextHasValue("XPATH", "//mat-grid-tile[3]/figure/mat-card/p", "Transfer to Own Account");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[4]/figure/mat-card/p", "Transfer via Phone/Email");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[5]/figure/mat-card/p", "Cardless Withdrawal");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[6]/figure/mat-card/p", "E-Naira");
		TestUtils.assertSearchText("XPATH", "//mat-grid-tile[7]/figure/mat-card/p", "Standing Order");
		Thread.sleep(500);
		
		//Rater Test
		TestUtils.raterTest("Transfers");
		
	}
	
	@Test
	public static void frequentTransfers() throws InterruptedException {
		TestUtils.testTitle("Navigate to Frequent Transfers");
		TestUtils.assertSearchText("XPATH", "//span/mat-panel-title", "Frequent Transfers");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Frequent Transfers has a GTBank tab");
		TestUtils.assertSearchText("XPATH", "//div[@class='mat-tab-label-content'][contains(text(), 'GTBank')]", "GTBank");
		Thread.sleep(500);
		
		TestUtils.testTitle("To confirm that the Frequent Transfers has a Other Banks tab");
		TestUtils.assertSearchText("XPATH", "//div[@class='mat-tab-label-content'][contains(text(), 'Other Banks')]", "Other Banks");
		Thread.sleep(500);
		
		
		TestUtils.testTitle("To confirm the 'Frequent Transfer' button auto hides the Go to Transfers section when clicked"  );
		//Click on Frequent Transfers Panel
		
		getDriver().findElement(By.xpath("//mat-panel-title")).click();
		Thread.sleep(500);
		if (getDriver().findElement(By.xpath("//div/button/span[contains(text(),'Go to Transfers')]")).isDisplayed()) {
			testInfo.get().error("<b> Go to Transfers section is not hidden </b>");
		} else {
			testInfo.get().info("<b> Go to Transfers section is hidden </b>");
		}
		
		
		
		
		
		
		
		/*
		 * TestUtils.
		 * testTitle("To confirm that all frequent transfers done to GTBank accounts are displayed when users click on the GTBank tab"
		 * ); try { if (getDriver().findElement(By.
		 * xpath("//div[@class='mat-tab-label-content'][contains(text(), 'GTBank')]")).
		 * isDisplayed())
		 * 
		 * { //testInfo.get().error(" Frequent Transfers are displayed ");
		 * 
		 * TestUtils.testTitle("Assert Number of Records"); int transferCount =
		 * getDriver().findElements(By.xpath("//div[@id='mat-tab-label-16-0']/div")).
		 * size();//Changed int to dimension
		 * 
		 * if (TestUtils.isElementPresent("XPATH", "//div[2]/div/div/div[2]")); {
		 * testInfo.get().info("Total Number of records displayed : <b>" + transferCount
		 * + "</b> "); } //else { testInfo.get().error("Table is empty"); } }
		 * 
		 * else { Thread.sleep(2000);
		 * testInfo.get().info("No record found for frequent transfers"); } }
		 * 
		 * 
		 * catch(Exception e) { TestUtils.assertSearchText("XPATH",
		 * "//mat-tab-body[@id='mat-tab-content-14-0']/div/frequent-transfers/div/p",
		 * " No Record - No frequent records "); Thread.sleep(500); }
		 */
		
		
		/*
		 * TestUtils.
		 * testTitle("To confirm that all frequent transfers done to Other Bank accounts are displayed when users click on the Other Banks tab"
		 * ); try { if (getDriver().findElement(By.
		 * xpath("//div[@class='mat-tab-label-content'][contains(text(), 'Other Banks')]"
		 * )).isDisplayed())
		 * 
		 * { testInfo.get().info(" Frequent Transfers are displayed ");
		 * 
		 * TestUtils.testTitle("Assert Number of Records"); int transferCount2 =
		 * getDriver().findElements(By.xpath("//div[@id='mat-tab-label-16-0']/div")).
		 * size();//Changed int to dimension
		 * 
		 * if (TestUtils.isElementPresent("XPATH", "//div[2]/div/div/div[2]")); {
		 * testInfo.get().info("Total Number of records displayed : <b>" +
		 * transferCount2 + "</b> "); } //else { testInfo.get().error("Table is empty");
		 * } }
		 * 
		 * else { Thread.sleep(2000);
		 * testInfo.get().info("No record found for frequent transfers"); } }
		 * 
		 * 
		 * catch(Exception e) { TestUtils.assertSearchText("XPATH",
		 * "//mat-tab-body[@id='mat-tab-content-14-0']/div/frequent-transfers/div/p",
		 * " No Record - No frequent records "); Thread.sleep(500); }
		 */
		
		//Click on the Frequent Transfer Panel
				//getDriver().findElement(By.xpath("//span/mat-panel-title")).click();
				//Thread.sleep(500);
				
				//Click on Frequent Transfers 
				
				/*
				 * getDriver().findElement(By.xpath("//mat-expansion-panel-header")).click();
				 * Thread.sleep(500);
				 * 
				 * //Click on other Banks
				 * getDriver().findElement(By.id("mat-tab-label-4-1")).click();
				 * Thread.sleep(500);
				 * 
				 * //CLick on Go to Transfers
				 * getDriver().findElement(By.xpath("//div/frequent-transfers/div/button")).
				 * click();
				 */
	}
	
		

}
