package com.example.tnr;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v133.network.Network;
import org.openqa.selenium.devtools.v133.network.model.ResponseReceived;
import java.util.Optional;
import java.util.Set;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class Hooks {
	public static WebDriver driver;
	private static WireMockServer wireMockServer;
	public static final String BASE_URL = "https://storefront:Monceau69@development.jacquemus.com/fr_fr";

	// https://www.jacquemus.com/fr_fr
	@Before("@Backend")
	public void setupWireMock() {
		// Start MockServer
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
		wireMockServer.start();
		configureFor("localhost", wireMockServer.port());
		WireMock.reset();

	}
	@Before("@Checkout") // Runs only for @ui-tagged scenarios
	public void setupSelenium() {
		// Initialize Selenium WebDriver for UI tests
		System.setProperty("webdriver.chrome.driver", "C:\\TNR\\chromedriver.exe");

		ChromeOptions chromeOptions = getDefaultChromeOptions();
		driver = new ChromeDriver(chromeOptions);
		driver.get(BASE_URL);
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie cookie : allCookies) {
			System.out.println("Name: " + cookie.getName());
			System.out.println("Value: " + cookie.getValue());
			System.out.println("-------------------");
		}

		// Identify the session cookie (name might vary)
		Cookie sessionCookie = driver.manage().getCookieNamed("_jacquemus_session");
		if (sessionCookie != null) {
			System.out.println("Session Cookie Name: " + sessionCookie.getName());
			System.out.println("Session Cookie Value: " + sessionCookie.getValue());
		}

	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			// Prendre une capture d'écran et l'attacher à Allure
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Screenshot");
				driver.quit();
		}
		if (wireMockServer != null && wireMockServer.isRunning()) {
			wireMockServer.stop();
		}

	}
	private ChromeOptions getDefaultChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=fr"); // Set browser language to French
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.setExperimentalOption("useAutomationExtension", false);
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);

		// For headless testing:
		// options.addArguments("--headless=new", "--window-size=1920,1080");

		return options;
	}
}
