package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import util.TestUtils;

public class CustomerProfile extends SelfService {
	@Test
	public void CustomerProfileFieldsCheck() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), WaitTime);
		
		TestUtils.testTitle("To View Customer Profile ");
		// Click on Customer Profile
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-grid-tile[1]/figure/mat-card")));
		getDriver().findElement(By.xpath("//mat-grid-tile[1]/figure/mat-card")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View Profile')]")));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Profile')]", "View Profile");
		TestUtils.assertSearchText("XPATH", "//gtibank-pageheader/div/div/div/p", "View your profile information. You can also change your profile image here.");
		Thread.sleep(500);
		
		//To confirm that All customer details are displayed.
		TestUtils.testTitle("To confirm that Customer Name, Email Address, Gender, PhoneNo and Address is displayed");
		TestUtils.assertSearchTextHasValue("XPATH", "//gtibank-view-profile/div/div/div/div[1]/p","Customer Name");
		TestUtils.assertSearchTextHasValue("XPATH", "//gtibank-view-profile/div/div/div/ul/li[1]/p","Customer Email");
		TestUtils.assertSearchTextHasValue("XPATH", "//gtibank-view-profile/div/div/div/ul/li[2]/p","Customer Gender");
		TestUtils.assertSearchTextHasValue("XPATH", "//gtibank-view-profile/div/div/div/ul/li[3]/p","Customer PhoneNumber");
		TestUtils.assertSearchTextHasValue("XPATH", "//gtibank-view-profile/div/div/div/ul/li[4]/p","Customer Address");
		
		Thread.sleep(1000);


	};
	
	@Test
	public void CustomerProfileImageUpload() throws InterruptedException {

		TestUtils.testTitle("To Upload Customer Profile Image ");
		TestUtils.uploadFile(By.id("userImageUpload"), "CustomerProfileImage.jpeg");
		TestUtils.AssertAlertMessage("Image updated successfully");
		Thread.sleep(500);
		
		/*TestUtils.testTitle("To View Customer Profile Image ");
		TestUtils.AssertImageExist(By.xpath("//gtibank-view-profile/div/div/div/div[1]/div"), "CustomerProfileImage.jpeg");*/
		

		TestUtils.uploadFile(By.id("userImageUpload"), "CustomerProfileImage.png"); 
		TestUtils.AssertAlertMessage("Image updated successfully");
		
		/*TestUtils.uploadFile(By.id("userImageUpload"), "CustomerProfileImage.pdf"); 
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div[1]/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");
		
		TestUtils.uploadFile(By.id("userImageUpload"), "CustomerProfileImage.xls"); 
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div[1]/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");
		
		TestUtils.uploadFile(By.id("userImageUpload"), "CustomerProfileImage.xslx"); 
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div[1]/div/div/p", "Wrong image file. Please upload only JPEG and PNG image files.");*/

		TestUtils.uploadFile(By.id("userImageUpload"), "Over200kbpic.jpg"); 
		TestUtils.assertSearchText("XPATH", "//ibank-notifications/div/div/div[1]/div/div/p", "Image is too large. Image size can not be greater than 200kb. Compress image or upload a different image.");

	}
	
}
