package com.utilities;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.jvm.hotspot.jdi.ThreadReferenceImpl;

import java.util.concurrent.TimeUnit;

public class WebOrdersBase {

    protected WebDriver driver;
    public Faker faker;

    @BeforeClass
    public void Setup() {
        WebDriverManager.chromedriver().setup();

    }


    @BeforeMethod
    public void SetupMethod() throws InterruptedException{

        driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        faker = new Faker();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

//    @AfterMethod
//    public void Close() throws InterruptedException{
//    Thread.sleep(4000);
//        driver.close();
//}


}