package com.tests;

import com.utilities.BrowserUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WONegativeLoginTests extends BrowserUtils {

    WebDriver driver;

    @BeforeClass
    public void BeforeClass() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeMethod
    public void SetupMethod() {
        driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx");
    }

    @Test (priority = 1)
    public void WONegativeLoginTestsWrongUsername() throws InterruptedException {
        String ActualTitle = driver.getTitle();
        String ExpectedTitle = "Web Orders Login";
        VerifyTextMatches(ActualTitle, ExpectedTitle);

        String actURL1 = driver.getCurrentUrl();
        Thread.sleep(2000);
        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Test");
        Thread.sleep(2000);

        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("Test");
        driver.findElement(By.name("ctl00$MainContent$login_button")).click();

        String ActTitle1 = driver.getTitle();
        String ExpectTitle1 = "Web Orders Login";
        VerifyTextMatches(ActTitle1, ExpectTitle1);

        String ActualURL2 = driver.getCurrentUrl();
        VerifyTextMatches(actURL1, ActualURL2);

    }

    @Test (priority = 2)
    public void WONegativeLoginTestsWrongPassword() throws InterruptedException {
        String ActualTitle = driver.getTitle();
        String ExpectedTitle = "Web Orders Login";
        VerifyTextMatches(ActualTitle, ExpectedTitle);

        String actURL1 = driver.getCurrentUrl();

        Thread.sleep(2000);
        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
        Thread.sleep(2000);
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$login_button")).click();

        String ActTitle1 = driver.getTitle();
        String ExpectTitle1 = "Web Orders Login";
        VerifyTextMatches(ActTitle1, ExpectTitle1);

        String ActualURL2 = driver.getCurrentUrl();
        VerifyTextMatches(actURL1, ActualURL2);


    }

    @Test (priority = 3)
    public void WONegativeLoginTestsBlankUsername() throws InterruptedException {

        String ActTitle1 = driver.getTitle();
        String ExpectTitle1 = "Web Orders Login";
        VerifyTextMatches(ActTitle1, ExpectTitle1);

        String CurrentURL1 = driver.getCurrentUrl();
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("Test");
        driver.findElement(By.name("ctl00$MainContent$login_button")).click();

        String ActTitle11 = driver.getTitle();
        VerifyTextMatches(ActTitle1, ExpectTitle1);

        String CurrentURL2 = driver.getCurrentUrl();
        VerifyTextMatches(CurrentURL2, CurrentURL1);


    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }
}