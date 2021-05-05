package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.browserstack.local.Local;

import testCases.Login;

public class TestBase {
	public static ExtentReports reports;
	public static ExtentHtmlReporter  htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	private static OptionsManager optionsManager = new OptionsManager();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private Local l;
	public String local="local";
	public String remoteJenkins="remote-jenkins";
	public String remoteBrowserStack="remote-browserStack";
	public static String toAddress;

	@Parameters ("testEnv")
	public static String myUrl(String testEnv) {

		String myUrl = null;
		if(testEnv.equalsIgnoreCase("stagingData")) {
			myUrl = System.getProperty("instance-url", "http://gtweb6.gtbank.com:8080/");
		} else
		{
			myUrl = System.getProperty("instance-url", "https://simrop.gloworld.com/simrop/");
		}

		return myUrl;
	}
	
	public static String gridUrl = System.getProperty("grid-url", "https://selenium.seamfix.com/wd/hub");
	
	@SuppressWarnings("deprecation")
	@BeforeSuite
    @Parameters({"groupReport","testEnv"})
	public void setUp( String groupReport, String testEnv) throws Exception {

		htmlReporter = new ExtentHtmlReporter (new File(System.getProperty("user.dir") + groupReport));
		reports = new ExtentReports();
		reports.setSystemInfo("TEST ENVIRONMENT", myUrl(testEnv));
		reports.attachReporter(htmlReporter);
	}
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Parameters({ "testEnv", "myBrowser", "config", "environment", "server"})
	@BeforeClass
	public void beforeClass(ITestContext iTestContext, String testEnv, String myBrowser, String config_file, String environment, String server) throws Exception{

		ExtentTest parent = reports.createTest(getClass().getName());
	    parentTest.set(parent);

		if (server.equals(remoteBrowserStack)) {

			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader("resources/conf/" + config_file));
			JSONObject envs = (JSONObject) config.get("environments");

			DesiredCapabilities capabilities = new DesiredCapabilities();

			Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
			Iterator it = envCapabilities.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
			}

			Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
			it = commonCapabilities.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				if(capabilities.getCapability(pair.getKey().toString()) == null){
					capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
				}
			}

			String username = System.getenv("BROWSERSTACK_USERNAME");
			if(username == null) {
				username = (String) config.get("user");
			}

			String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			if(accessKey == null) {
				accessKey = (String) config.get("key");
			}

			if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
				l = new Local();
				Map<String, String> options = new HashMap<String, String>();
				options.put("key", accessKey);
				l.start(options);
			}
			driver.set( new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities));
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			getDriver().get(myUrl(testEnv));

		}else if(server.equals(remoteJenkins)) {

			    // Remote Server Directory
				String browser = "chrome";
				if(browser.equalsIgnoreCase(myBrowser)) {
					DesiredCapabilities capability = DesiredCapabilities.chrome();
					capability.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", myUrl(testEnv) +" ["+ iTestContext.getName()+"]");
					capability.setPlatform(Platform.ANY);
					capability.setCapability("idleTimeout", 300);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));

				}
				else {
					DesiredCapabilities capability = DesiredCapabilities.firefox();
					capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
					capability.setCapability(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE, optionsManager.getFirefoxOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", myUrl(testEnv) +" ["+ iTestContext.getName()+"]");
					capability.setPlatform(Platform.ANY);
					capability.setCapability("idleTimeout", 300);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
				}
				getDriver().manage().window().maximize();
				getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				getDriver().get(myUrl(testEnv));


		}else if(server.equals(local)) {
				// Local Directory
			File classpathRoot = new File(System.getProperty("user.dir"));
            File chromeDriver = new File(classpathRoot, "chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
            driver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		 }

		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		getDriver().get(myUrl(testEnv));
	}

	@Parameters ({"testEnv"})
	@Test 
	public void Login(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("Login");

		String validUserID = (String) envs.get("validUserID");
		String pw = (String) envs.get("pw");
		
		TestUtils.getStartedPage();
		
		TestUtils.testTitle("Login with valid User ID : (" + validUserID + ") and valid password: (" + pw + ")");
		Login.loginTest(testEnv, validUserID);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mat-body.small.ng-tns-c3-0")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Login Successful')]", "Login Successful");
		getDriver().findElement(By.xpath("//mat-card/div/button/span/mat-icon")).click();
		//Assert.assertEquals(getDriver().getTitle(), "Landing | GTB-iBank");
		Thread.sleep(1000);
	}
	
	
	@Test 
	public void ClickOnProceedToInternetBankingButtonTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		// Click on Proceed to Internet Banking button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clearLoader")));
		getDriver().findElement(By.id("clearLoader")).click();
		Thread.sleep(500);
	}
	
	@AfterClass
	public void afterClass() {
		 getDriver().quit();
	}

	public static  WebDriver getDriver(){
		return driver.get();
	}
	
	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) throws InterruptedException {

		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().getModel().setDescription(TestUtils.CheckBrowser());
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			Thread.sleep(1000);
			}
		
	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		for (String group : result.getMethod().getGroups())
			testInfo.get().assignCategory(group);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = TestUtils.addScreenshot();
			 testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
			testInfo.get().fail(result.getThrowable());
			getDriver().navigate().refresh();
		}			
        else if (result.getStatus() == ITestResult.SKIP)
        	testInfo.get().skip(result.getThrowable());
        else
        	testInfo.get().pass(result.getName() +" Test passed");

		reports.flush();
	}
	
	@Parameters({"toMails", "groupReport"})
    @AfterSuite(description = "clean up report after test suite")
    public void cleanup(String toMails, String groupReport) {

        toAddress = System.getProperty("email_list", toMails);
        SendMail.ComposeGmail("IBANK Report <gtbankuct.test.report@gmail.com>", toAddress, groupReport);

    }
}
