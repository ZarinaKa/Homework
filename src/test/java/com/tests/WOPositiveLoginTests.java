package com.tests;

import com.utilities.BrowserUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WOPositiveLoginTests extends BrowserUtils {

    WebDriver driver;

    @BeforeClass
  public void  BeforeClass() {
    WebDriverManager.chromedriver().setup();

    }


    @BeforeMethod
    public void SetupMethod() {
        driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx");

    }

    @Test
public void WebOrders(){
     String ActualTitle = driver.getTitle();
     String ExpectedTitle = "Web Orders Login";
     VerifyTextMatches(ActualTitle,ExpectedTitle);
        System.out.println("pass");

        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
   driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
driver.findElement(By.name("ctl00$MainContent$login_button")).click();

        String ActTitle1 = driver.getTitle();
        String ExpectTitle1 ="Web Orders";
        VerifyTextMatches(ActTitle1,ExpectTitle1);
        System.out.println("pass");

        String ActUrl = driver.getCurrentUrl();
        String ExpectUrl = "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/";
        VerifyTextMatches(ActUrl,ExpectUrl);
        System.out.println("pass");

    }






}
