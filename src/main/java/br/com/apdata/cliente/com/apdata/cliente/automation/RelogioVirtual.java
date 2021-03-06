package br.com.apdata.cliente.com.apdata.cliente.automation;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.apdata.cliente.com.apdata.cliente.system.OSValidator;

@Service
public class RelogioVirtual {

	@Value("${apdata.url}")
	private String PORTAL_DE_RH;

	@Value("${apdata.user}")
	private String USER;

	@Value("${apdata.password}")
	private String PASSWORD;
	private static final Logger log = LoggerFactory.getLogger(RelogioVirtual.class);

	private static WebDriver driver;

	public void callMarcacao() {

		this.setup();
		this.openPageAndConfirmCookies();

		log.info("Call marcação");

		log.info("Preenche o usuário");
		driver.findElement(By.id("ext-131")).sendKeys(USER);

		log.info("Preenche a senha");
		driver.findElement(By.id("ext-133")).sendKeys(PASSWORD);

		log.info("Clica no botão Marcação");
		driver.findElement(By.id("ext-135")).click();

	}

	private void openPageAndConfirmCookies() {

		log.info("Go to home page");
		driver.get(PORTAL_DE_RH);

		// Accept Cookie LOL
		moveToLastWindowsHandle();
		log.info("Clica no botão confirmar preferências");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-1020")));

		driver.findElement(By.id("button-1020")).click();

	}

	private void setup() {

		String currentDir = System.getProperty("user.dir");
		log.info("Current dir using System:" + currentDir);

		log.info("Setting webdriver for Google Chrome, find file chromedriver.exe in directory" + currentDir);

		currentDir = OSValidator.isWindows() ? currentDir.concat(File.separator + "chromedriver.exe")
				: currentDir.concat(File.separator + "chromedriver");

		log.info("Current Dir" + currentDir);

		System.setProperty("webdriver.chrome.driver", currentDir);

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);

		driver = new ChromeDriver(chromeOptions);

	}

	public static void moveToLastWindowsHandle() {
		driver.switchTo().window(
				driver.getWindowHandles().stream().skip(driver.getWindowHandles().size() - 1).findFirst().get());
	}
}
