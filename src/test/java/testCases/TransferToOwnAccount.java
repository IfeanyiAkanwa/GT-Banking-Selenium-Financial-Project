package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.TestBase;
import util.TestUtils;

public class TransferToOwnAccount extends TestBase{
	
	public void navigateToTransferToOwnAccount() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		TestUtils.testTitle("Navigate To Own Account Transfer");
		
		//CLick on Own Account Transfers
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		getDriver().findElement(By.xpath("")).click();
		Thread.sleep(500);
		
		
		
	}

}
