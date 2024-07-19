package org.vaadin.textfieldformatter.it;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.vaadin.textfieldformatter.AbstractTest;
import org.vaadin.textfieldformatter.UITestConfiguration;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractCustomTestBenchTestCase extends TestBenchTestCase {
	protected static final int TESTPORT = 8080;
	protected static final String BASEURL = "http://localhost:" + TESTPORT + "/";

	@Rule
	public ScreenshotOnFailureRule screenshotOnFailure = new ScreenshotOnFailureRule(this, true);

	public AbstractCustomTestBenchTestCase() {
		super();
	}

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	protected void startBrowser() {
		startBrowser(null);
	}

	protected void startBrowser(WebDriver driver) {
		driver = new ChromeDriver();
		setDriver(driver);
	}

	protected <T extends org.vaadin.textfieldformatter.AbstractTest, U extends UITestConfiguration> void openUI(
			Class<T> testClass, Class<? extends UITestConfiguration> configuration) {
		driver.navigate().to(BASEURL + testClass.getSimpleName().toLowerCase() + "/" + configuration.getSimpleName());
	}

	protected <T extends AbstractTest> void openUI(Class<T> testClass) {
		driver.get(BASEURL + testClass.getSimpleName().toLowerCase());
	}
}
